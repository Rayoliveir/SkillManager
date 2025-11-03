package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.*;
import com.senai.skillmanager.model.empresa.Empresa;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.estagiario.DadosAcademicos;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.repository.EmpresaRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.FaculdadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstagiarioService {

    private final EstagiarioRepository estagiarioRepository;
    private final FaculdadeRepository faculdadeRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public EstagiarioService(EstagiarioRepository estagiarioRepository, FaculdadeRepository faculdadeRepository, EmpresaRepository empresaRepository, PasswordEncoder passwordEncoder) {
        this.estagiarioRepository = estagiarioRepository;
        this.faculdadeRepository = faculdadeRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public EstagiarioResponseDTO salvar(EstagiarioDTO estagiarioDTO) {
        estagiarioRepository.findByCpf(estagiarioDTO.getCpf()).ifPresent(unused -> {
            throw new RuntimeException("CPF já cadastrado.");
        });
        estagiarioRepository.findByEmail(estagiarioDTO.getEmail()).ifPresent(unused -> {
            throw new RuntimeException("Email já cadastrado.");
        });

        Empresa empresa = empresaRepository.findByCodigoEmpresa(estagiarioDTO.getCodigoEmpresa())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o código: " + estagiarioDTO.getCodigoEmpresa()));

        DadosAcademicosDTO dadosAcademicosDTO = estagiarioDTO.getDadosAcademicos();
        Faculdade faculdade = faculdadeRepository.findByCnpj(dadosAcademicosDTO.getFaculdadeCnpj())
                .orElseThrow(() -> new EntityNotFoundException("Faculdade não encontrada com CNPJ: " + dadosAcademicosDTO.getFaculdadeCnpj()));

        DadosAcademicos dadosAcademicos = new DadosAcademicos();
        dadosAcademicos.setFaculdade(faculdade);
        dadosAcademicos.setCurso(dadosAcademicosDTO.getCurso());
        dadosAcademicos.setPeriodoSemestre(dadosAcademicosDTO.getPeriodoSemestre());
        dadosAcademicos.setPrevisaoFormatura(dadosAcademicosDTO.getPrevisaoFormatura());
        dadosAcademicos.setRa(dadosAcademicosDTO.getRa());

        EnderecoDTO enderecoDTO = estagiarioDTO.getEndereco();
        Endereco endereco = new Endereco();
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setEstados(enderecoDTO.getEstados());
        endereco.setCep(enderecoDTO.getCep());

        Estagiario estagiario = new Estagiario();
        estagiario.setNome(estagiarioDTO.getNome());
        estagiario.setDataNascimento(estagiarioDTO.getDataNascimento());
        estagiario.setGenero(estagiarioDTO.getGenero());
        estagiario.setTelefone(estagiarioDTO.getTelefone());
        estagiario.setEmail(estagiarioDTO.getEmail());
        estagiario.setCpf(estagiarioDTO.getCpf());
        estagiario.setSenha(passwordEncoder.encode(estagiarioDTO.getSenha()));
        estagiario.setEndereco(endereco);
        estagiario.setDadosAcademicos(dadosAcademicos);
        estagiario.setEmpresa(empresa);

        Estagiario estagiarioSalvo = estagiarioRepository.save(estagiario);
        return toResponseDTO(estagiarioSalvo);
    }

    @Transactional
    public EstagiarioResponseDTO atualizar(Long id, EstagiarioDTO estagiarioDTO, Authentication authentication) {
        Estagiario estagiarioExistente = estagiarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado com ID: " + id));

        checkOwnership(estagiarioExistente, authentication, true);

        estagiarioExistente.setNome(estagiarioDTO.getNome());
        estagiarioExistente.setDataNascimento(estagiarioDTO.getDataNascimento());
        estagiarioExistente.setGenero(estagiarioDTO.getGenero());
        estagiarioExistente.setTelefone(estagiarioDTO.getTelefone());

        if (estagiarioDTO.getSenha() != null && !estagiarioDTO.getSenha().isEmpty()) {
            estagiarioExistente.setSenha(passwordEncoder.encode(estagiarioDTO.getSenha()));
        }

        if (estagiarioDTO.getEndereco() != null) {
            EnderecoDTO enderecoDTO = estagiarioDTO.getEndereco();
            Endereco endereco = estagiarioExistente.getEndereco() != null ? estagiarioExistente.getEndereco() : new Endereco();
            endereco.setLogradouro(enderecoDTO.getLogradouro());
            endereco.setBairro(enderecoDTO.getBairro());
            endereco.setCidade(enderecoDTO.getCidade());
            endereco.setNumero(enderecoDTO.getNumero());
            endereco.setEstados(enderecoDTO.getEstados());
            endereco.setCep(enderecoDTO.getCep());
            estagiarioExistente.setEndereco(endereco);
        }

        Estagiario estagiarioAtualizado = estagiarioRepository.save(estagiarioExistente);
        return toResponseDTO(estagiarioAtualizado);
    }

    public void excluir(Long id) {
        if (!estagiarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Estagiário não encontrado com ID: " + id);
        }
        estagiarioRepository.deleteById(id);
    }

    public List<EstagiarioResponseDTO> listarTodos() {
        return estagiarioRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public EstagiarioResponseDTO buscarPorId(Long id, Authentication authentication) {
        Estagiario estagiario = estagiarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado com ID: " + id));

        checkOwnership(estagiario, authentication, false);

        return toResponseDTO(estagiario);
    }

    private void checkOwnership(Estagiario estagiario, Authentication authentication, boolean isUpdate) {
        String authEmail = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if (isAdmin) return;

        boolean isEstagiario = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"));

        if (isEstagiario) {
            if (!estagiario.getEmail().equals(authEmail)) {
                throw new SecurityException("Acesso negado. Você não tem permissão para acessar este recurso.");
            }
            return;
        }

        if (isUpdate) {
            throw new SecurityException("Acesso negado. Apenas o próprio estagiário ou um ADMIN pode atualizar este recurso.");
        }

        boolean isSupervisorOuGerente = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GERENTE"));

        boolean isFaculdade = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_FACULDADE"));

        if (isSupervisorOuGerente || isFaculdade) {
            return;
        }

        throw new SecurityException("Acesso negado. Você não tem permissão para acessar este recurso.");
    }

    public EstagiarioResponseDTO toResponseDTO(Estagiario estagiario) {
        if (estagiario == null) return null;
        EstagiarioResponseDTO dto = new EstagiarioResponseDTO();
        dto.setId(estagiario.getId());
        dto.setNome(estagiario.getNome());
        dto.setDataNascimento(estagiario.getDataNascimento());
        dto.setGenero(estagiario.getGenero());
        dto.setTelefone(estagiario.getTelefone());
        dto.setEmail(estagiario.getEmail());
        dto.setCpf(estagiario.getCpf());
        if (estagiario.getEndereco() != null) {
            dto.setEndereco(toEnderecoResponseDTO(estagiario.getEndereco()));
        }
        if (estagiario.getDadosAcademicos() != null) {
            dto.setDadosAcademicos(toDadosAcademicosResponseDTO(estagiario.getDadosAcademicos()));
        }
        return dto;
    }

    public EnderecoResponseDTO toEnderecoResponseDTO(Endereco endereco) {
        if (endereco == null) return null;
        EnderecoResponseDTO dto = new EnderecoResponseDTO();
        dto.setId(endereco.getId());
        dto.setLogradouro(endereco.getLogradouro());
        dto.setBairro(endereco.getBairro());
        dto.setCidade(endereco.getCidade());
        dto.setNumero(endereco.getNumero());
        dto.setEstados(endereco.getEstados());
        dto.setCep(endereco.getCep());
        return dto;
    }

    public DadosAcademicosResponseDTO toDadosAcademicosResponseDTO(DadosAcademicos dados) {
        if (dados == null) return null;

        DadosAcademicosResponseDTO dto = new DadosAcademicosResponseDTO();
        dto.setId(dados.getId());
        dto.setFaculdade(toFaculdadeResponseDTO(dados.getFaculdade()));
        dto.setCurso(dados.getCurso());
        dto.setPeriodoSemestre(dados.getPeriodoSemestre());
        dto.setPrevisaoFormatura(dados.getPrevisaoFormatura());
        dto.setRa(dados.getRa());
        return dto;
    }

    public FaculdadeResponseDTO toFaculdadeResponseDTO(Faculdade faculdade) {
        if (faculdade == null) return null;

        FaculdadeResponseDTO response = new FaculdadeResponseDTO();
        response.setId(faculdade.getId());
        response.setNome(faculdade.getNome());
        response.setCnpj(faculdade.getCnpj());
        response.setTelefone(faculdade.getTelefone());
        response.setSite(faculdade.getSite());

        if (faculdade.getEndereco() != null) {
            response.setEndereco(toEnderecoDTO(faculdade.getEndereco()));
        }

        return response;
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