package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.*;
import com.senai.skillmanager.model.Empresa;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.estagiario.DadosAcademicos;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.repository.EmpresaRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.FaculdadeRepository;
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
        estagiarioRepository.findByCpf(estagiarioDTO.getCpf()).ifPresent(e -> {
            throw new RuntimeException("CPF já cadastrado.");
        });
        estagiarioRepository.findByEmail(estagiarioDTO.getEmail()).ifPresent(e -> {
            throw new RuntimeException("Email já cadastrado.");
        });

        Empresa empresa = empresaRepository.findByCnpj(estagiarioDTO.getEmpresaCnpj())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com CNPJ: " + estagiarioDTO.getEmpresaCnpj()));

        DadosAcademicosDTO dadosAcademicosDTO = estagiarioDTO.getDadosAcademicos();
        Faculdade faculdade = faculdadeRepository.findById(dadosAcademicosDTO.getFaculdadeId())
                .orElseThrow(() -> new RuntimeException("Faculdade não encontrada com ID: " + dadosAcademicosDTO.getFaculdadeId()));

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
    public EstagiarioResponseDTO atualizar(Long id, EstagiarioDTO estagiarioDTO) {
        Estagiario estagiarioExistente = estagiarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estagiário não encontrado com ID: " + id));

        estagiarioExistente.setNome(estagiarioDTO.getNome());
        if (estagiarioDTO.getSenha() != null && !estagiarioDTO.getSenha().isEmpty()) {
            estagiarioExistente.setSenha(passwordEncoder.encode(estagiarioDTO.getSenha()));
        }
        Estagiario estagiarioAtualizado = estagiarioRepository.save(estagiarioExistente);
        return toResponseDTO(estagiarioAtualizado);
    }

    public void excluir(Long id) {
        if (!estagiarioRepository.existsById(id)) {
            throw new RuntimeException("Estagiário não encontrado com ID: " + id);
        }
        estagiarioRepository.deleteById(id);
    }

    public List<EstagiarioResponseDTO> listarTodos() {
        return estagiarioRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public EstagiarioResponseDTO buscarPorId(Long id) {
        Estagiario estagiario = estagiarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Estagiário não encontrado com ID: " + id));
        return toResponseDTO(estagiario);
    }

    public EstagiarioResponseDTO toResponseDTO(Estagiario estagiario) {
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

    private EnderecoResponseDTO toEnderecoResponseDTO(Endereco endereco) {
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

    private DadosAcademicosResponseDTO toDadosAcademicosResponseDTO(DadosAcademicos dados) {
        DadosAcademicosResponseDTO dto = new DadosAcademicosResponseDTO();
        dto.setId(dados.getId());
        dto.setFaculdade(dados.getFaculdade());
        dto.setCurso(dados.getCurso());
        dto.setPeriodoSemestre(dados.getPeriodoSemestre());
        dto.setPrevisaoFormatura(dados.getPrevisaoFormatura());
        dto.setRa(dados.getRa());
        return dto;
    }
}