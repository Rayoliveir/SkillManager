package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.*;
import com.senai.skillmanager.model.empresa.Empresa;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.Endereco;
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
        supervisorRepository.findByEmail(dto.getEmail()).ifPresent(f -> {
            throw new RuntimeException("Email já cadastrado.");
        });

        Empresa empresa;
        Optional<Empresa> empresaOpt = empresaRepository.findByCnpj(dto.getEmpresaCnpj());

        // --- LÓGICA DO FLUXO A e B (Get or Create) ---
        if (empresaOpt.isPresent()) {
            // FLUXO B: Empresa já existe, apenas vincule
            empresa = empresaOpt.get();
        } else {
            // FLUXO A: Empresa não existe, crie-a

            // Validação: Para criar, os campos extras são obrigatórios
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
        // --------------------------------------------

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

    public SupervisorResponseDTO buscarPorId(Long id) {
        Supervisor supervisor = supervisorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado com ID: " + id));
        return toResponseDTO(supervisor);
    }

    @Transactional
    public SupervisorResponseDTO atualizar(Long id, SupervisorDTO dto) {
        Supervisor supervisorExistente = supervisorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado com ID: " + id));


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

    // --- MÉTODOS HELPERS ATUALIZADOS ---

    private SupervisorResponseDTO toResponseDTO(Supervisor supervisor) {
        SupervisorResponseDTO response = new SupervisorResponseDTO();
        response.setId(supervisor.getId());
        response.setNome(supervisor.getNome());
        response.setEmail(supervisor.getEmail());
        response.setCargo(supervisor.getCargo());
        response.setEmpresa(toEmpresaResponseDTO(supervisor.getEmpresa()));
        return response;
    }

    private EmpresaResponseDTO toEmpresaResponseDTO(Empresa empresa) {
        EmpresaResponseDTO dto = new EmpresaResponseDTO();
        dto.setId(empresa.getId());
        dto.setNome(empresa.getNome());
        dto.setRazaoSocial(empresa.getRazaoSocial());
        dto.setCnpj(empresa.getCnpj());
        dto.setTipoEmpresa(empresa.getTipoEmpresa());
        dto.setInscricaoEstadual(empresa.getInscricaoEstadual());
        dto.setInscricaoMunicipal(empresa.getInscricaoMunicipal());

        if (empresa.getEndereco() != null) {
            dto.setEndereco(toEnderecoDTO(empresa.getEndereco()));
        }
        return dto;
    }

    private EnderecoDTO toEnderecoDTO(Endereco endereco) {
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