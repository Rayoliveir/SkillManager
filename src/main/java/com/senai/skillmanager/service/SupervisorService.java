package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.*;
import com.senai.skillmanager.model.empresa.Empresa;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.repository.EmpresaRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private final PasswordEncoder passwordEncoder;
    private final EmpresaService empresaService;

    public SupervisorService(SupervisorRepository supervisorRepository,
                             EmpresaRepository empresaRepository,
                             PasswordEncoder passwordEncoder,
                             @Lazy EmpresaService empresaService) {
        this.supervisorRepository = supervisorRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
        this.empresaService = empresaService;
    }

    @Transactional
    public SupervisorResponseDTO salvar(SupervisorDTO dto) {
        supervisorRepository.findByEmail(dto.getEmail()).ifPresent(unused -> {
            throw new RuntimeException("Email já cadastrado.");
        });

        Empresa empresa;
        Optional<Empresa> empresaOpt = empresaRepository.findByCnpj(dto.getEmpresaCnpj());

        if (empresaOpt.isPresent()) {
            empresa = empresaOpt.get();
        } else {
            if (dto.getEmpresaNome() == null || dto.getEmpresaRazaoSocial() == null ||
                    dto.getEmpresaTipo() == null || dto.getEmpresaEndereco() == null) {

                throw new IllegalArgumentException("Empresa com CNPJ " + dto.getEmpresaCnpj() + " não encontrada. " +
                        "Para cadastrá-la, forneça todos os dados da empresa (Nome, Razão Social, Tipo e Endereço).");
            }

            EmpresaDTO novaEmpresaDTO = new EmpresaDTO();
            novaEmpresaDTO.setNome(dto.getEmpresaNome());
            novaEmpresaDTO.setRazaoSocial(dto.getEmpresaRazaoSocial());
            novaEmpresaDTO.setCnpj(dto.getEmpresaCnpj());
            novaEmpresaDTO.setTipoEmpresa(dto.getEmpresaTipo());
            novaEmpresaDTO.setInscricaoEstadual(dto.getEmpresaInscricaoEstadual());
            novaEmpresaDTO.setInscricaoMunicipal(dto.getEmpresaInscricaoMunicipal());
            novaEmpresaDTO.setEndereco(dto.getEmpresaEndereco());

            EmpresaResponseDTO empresaSalva = empresaService.salvar(novaEmpresaDTO);
            empresa = empresaService.buscarEntidadePorId(empresaSalva.getId());
        }

        Supervisor supervisor = new Supervisor();
        supervisor.setNome(dto.getNome());
        supervisor.setEmail(dto.getEmail());
        supervisor.setSenha(passwordEncoder.encode(dto.getSenha()));
        supervisor.setCargo(dto.getCargo());
        supervisor.setEmpresa(empresa);

        Supervisor supervisorSalvo = supervisorRepository.save(supervisor);
        return toResponseDTO(supervisorSalvo);
    }

    public List<SupervisorResponseDTO> listarTodos() {
        return supervisorRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public SupervisorResponseDTO buscarPorId(Long id, Authentication authentication) {
        Supervisor supervisor = supervisorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado com ID: " + id));

        checkOwnership(supervisor, authentication);

        return toResponseDTO(supervisor);
    }

    @Transactional
    public SupervisorResponseDTO atualizar(Long id, SupervisorDTO dto, Authentication authentication) {
        Supervisor supervisorExistente = supervisorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado com ID: " + id));

        checkOwnership(supervisorExistente, authentication);

        Empresa empresa = empresaRepository.findByCnpj(dto.getEmpresaCnpj())
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma empresa encontrada com o CNPJ: " + dto.getEmpresaCnpj()));

        supervisorExistente.setNome(dto.getNome());
        supervisorExistente.setEmail(dto.getEmail());
        supervisorExistente.setCargo(dto.getCargo());
        supervisorExistente.setEmpresa(empresa);

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            supervisorExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Supervisor supervisorAtualizado = supervisorRepository.save(supervisorExistente);
        return toResponseDTO(supervisorAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!supervisorRepository.existsById(id)) {
            throw new EntityNotFoundException("Supervisor não encontrado com ID: " + id);
        }
        supervisorRepository.deleteById(id);
    }

    private void checkOwnership(Supervisor supervisor, Authentication authentication) {
        String authEmail = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (isAdmin) {
            return;
        }

        if (!supervisor.getEmail().equals(authEmail)) {
            throw new SecurityException("Acesso negado. Você não tem permissão para acessar este recurso.");
        }
    }

    public SupervisorResponseDTO toResponseDTO(Supervisor supervisor) {
        if (supervisor == null) return null;
        SupervisorResponseDTO response = new SupervisorResponseDTO();
        response.setId(supervisor.getId());
        response.setNome(supervisor.getNome());
        response.setEmail(supervisor.getEmail());
        response.setCargo(supervisor.getCargo());
        response.setEmpresa(toEmpresaResponseDTO(supervisor.getEmpresa()));
        return response;
    }

    public EmpresaResponseDTO toEmpresaResponseDTO(Empresa empresa) {
        if (empresa == null) return null;
        EmpresaResponseDTO dto = new EmpresaResponseDTO();
        dto.setId(empresa.getId());
        dto.setNome(empresa.getNome());
        dto.setRazaoSocial(empresa.getRazaoSocial());
        dto.setCnpj(empresa.getCnpj());
        dto.setCodigoEmpresa(empresa.getCodigoEmpresa());
        dto.setTipoEmpresa(empresa.getTipoEmpresa());
        dto.setInscricaoEstadual(empresa.getInscricaoEstadual());
        dto.setInscricaoMunicipal(empresa.getInscricaoMunicipal());

        if (empresa.getEndereco() != null) {
            dto.setEndereco(toEnderecoDTO(empresa.getEndereco()));
        }
        return dto;
    }

    public EnderecoDTO toEnderecoDTO(Endereco endereco) {
        if (endereco == null) return null;
        EnderecoDTO dto = new EnderecoDTO();
        dto.setLogradouro(endereco.getLogradouro());
        dto.setBairro(endereco.getBairro());
        dto.setCidade(endereco.getCidade());
        dto.setNumero(endereco.getNumero());
        dto.setEstados(endereco.getEstados());
        dto.setCep(endereco.getCep());
        return dto;
    }
}