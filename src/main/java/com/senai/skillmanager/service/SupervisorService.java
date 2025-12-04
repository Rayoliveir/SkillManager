package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.EmpresaDTO;
import com.senai.skillmanager.dto.SupervisorDTO;
import com.senai.skillmanager.dto.SupervisorResponseDTO;
import com.senai.skillmanager.model.empresa.Empresa;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.repository.EmpresaRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupervisorService {

    private final SupervisorRepository supervisorRepository;
    private final EmpresaRepository empresaRepository;
    private final EmpresaService empresaService;
    private final PasswordEncoder passwordEncoder;

    public SupervisorService(SupervisorRepository supervisorRepository,
                             EmpresaRepository empresaRepository,
                             @Lazy EmpresaService empresaService,
                             PasswordEncoder passwordEncoder) {
        this.supervisorRepository = supervisorRepository;
        this.empresaRepository = empresaRepository;
        this.empresaService = empresaService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SupervisorResponseDTO salvar(SupervisorDTO dto) {
        if (dto.getSenha() == null || dto.getSenha().isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória para o cadastro.");
        }

        if (supervisorRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um supervisor cadastrado com este e-mail.");
        }

        Supervisor supervisor = new Supervisor();
        supervisor.setNome(dto.getNome());
        supervisor.setEmail(dto.getEmail());
        supervisor.setSenha(passwordEncoder.encode(dto.getSenha()));
        supervisor.setCargo(dto.getCargo());

        // Lógica da Empresa: Busca por CNPJ ou Cria
        Optional<Empresa> empresaExistente = empresaRepository.findByCnpj(dto.getEmpresaCnpj());

        if (empresaExistente.isPresent()) {
            supervisor.setEmpresa(empresaExistente.get());
        } else {
            EmpresaDTO novaEmpresaDTO = new EmpresaDTO();
            novaEmpresaDTO.setNome(dto.getEmpresaNome());
            novaEmpresaDTO.setRazaoSocial(dto.getEmpresaRazaoSocial());
            novaEmpresaDTO.setCnpj(dto.getEmpresaCnpj());
            novaEmpresaDTO.setTipoEmpresa(dto.getEmpresaTipo());
            novaEmpresaDTO.setInscricaoEstadual(dto.getEmpresaInscricaoEstadual());
            novaEmpresaDTO.setInscricaoMunicipal(dto.getEmpresaInscricaoMunicipal());
            novaEmpresaDTO.setEndereco(dto.getEmpresaEndereco());

            empresaService.salvar(novaEmpresaDTO);
            supervisor.setEmpresa(empresaService.buscarEntidadePorCnpj(dto.getEmpresaCnpj()));
        }

        Supervisor salvo = supervisorRepository.save(supervisor);
        return toResponseDTO(salvo);
    }

    public List<SupervisorResponseDTO> listarTodos() {
        return supervisorRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public SupervisorResponseDTO buscarPorId(Long id) {
        Supervisor supervisor = supervisorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado com ID: " + id));
        return toResponseDTO(supervisor);
    }

    @Transactional
    public void excluir(Long id) {
        if (!supervisorRepository.existsById(id)) {
            throw new EntityNotFoundException("Supervisor não encontrado com ID: " + id);
        }
        supervisorRepository.deleteById(id);
    }

    @Transactional
    public SupervisorResponseDTO atualizar(Long id, SupervisorDTO dto) {
        Supervisor supervisor = supervisorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado."));

        supervisor.setNome(dto.getNome());
        supervisor.setEmail(dto.getEmail());
        supervisor.setCargo(dto.getCargo());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            supervisor.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Supervisor atualizado = supervisorRepository.save(supervisor);
        return toResponseDTO(atualizado);
    }

    public SupervisorResponseDTO toResponseDTO(Supervisor entity) {
        if (entity == null) return null;
        SupervisorResponseDTO dto = new SupervisorResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setCargo(entity.getCargo());
        if (entity.getEmpresa() != null) {
            dto.setEmpresa(empresaService.toResponseDTO(entity.getEmpresa()));
        }
        return dto;
    }
}