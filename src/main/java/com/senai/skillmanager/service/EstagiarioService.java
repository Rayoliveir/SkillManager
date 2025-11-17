package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.*;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.empresa.Empresa;
import com.senai.skillmanager.model.estagiario.DadosAcademicos;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.EmpresaRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.FaculdadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EstagiarioService {

    private final EstagiarioRepository estagiarioRepository;
    private final FaculdadeRepository faculdadeRepository;
    private final EmpresaRepository empresaRepository;
    private final CoordenadorRepository coordenadorRepository;
    private final PasswordEncoder passwordEncoder;
    private final DadosAcademicosService dadosAcademicosService;
    private final EnderecoService enderecoService;

    public EstagiarioService(EstagiarioRepository estagiarioRepository,
                             FaculdadeRepository faculdadeRepository,
                             EmpresaRepository empresaRepository,
                             CoordenadorRepository coordenadorRepository,
                             PasswordEncoder passwordEncoder,
                             @Lazy DadosAcademicosService dadosAcademicosService,
                             EnderecoService enderecoService) {
        this.estagiarioRepository = estagiarioRepository;
        this.faculdadeRepository = faculdadeRepository;
        this.empresaRepository = empresaRepository;
        this.coordenadorRepository = coordenadorRepository;
        this.passwordEncoder = passwordEncoder;
        this.dadosAcademicosService = dadosAcademicosService;
        this.enderecoService = enderecoService;
    }


    @Transactional
    public EstagiarioResponseDTO salvar(EstagiarioDTO dto) {
        if (estagiarioRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new RuntimeException("CPF já cadastrado.");
        }
        if (estagiarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado.");
        }

        Empresa empresa = empresaRepository.findByCodigoEmpresa(dto.getCodigoEmpresa())
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o código: " + dto.getCodigoEmpresa()));

        Faculdade faculdade = faculdadeRepository.findByCnpj(dto.getDadosAcademicos().getFaculdadeCnpj())
                .orElseThrow(() -> new EntityNotFoundException("Faculdade não encontrada com CNPJ: " + dto.getDadosAcademicos().getFaculdadeCnpj()));

        Estagiario estagiario = new Estagiario();
        estagiario.setNome(dto.getNome());
        estagiario.setDataNascimento(dto.getDataNascimento());
        estagiario.setGenero(dto.getGenero());
        estagiario.setTelefone(dto.getTelefone());
        estagiario.setEmail(dto.getEmail());
        estagiario.setCpf(dto.getCpf());
        estagiario.setSenha(passwordEncoder.encode(dto.getSenha()));
        estagiario.setEmpresa(empresa);

        DadosAcademicos dadosAcademicos = new DadosAcademicos();
        dadosAcademicos.setFaculdade(faculdade);
        dadosAcademicos.setCurso(dto.getDadosAcademicos().getCurso());
        dadosAcademicos.setPeriodoSemestre(dto.getDadosAcademicos().getPeriodoSemestre());
        dadosAcademicos.setPrevisaoFormatura(YearMonth.parse(dto.getDadosAcademicos().getPrevisaoFormatura()));
        dadosAcademicos.setRa(dto.getDadosAcademicos().getRa());
        estagiario.setDadosAcademicos(dadosAcademicos);

        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.getEndereco().getLogradouro());
        endereco.setBairro(dto.getEndereco().getBairro());
        endereco.setCidade(dto.getEndereco().getCidade());
        endereco.setNumero(dto.getEndereco().getNumero());
        endereco.setEstados(dto.getEndereco().getEstados());
        endereco.setCep(dto.getEndereco().getCep());
        estagiario.setEndereco(endereco);

        Estagiario estagiarioSalvo = estagiarioRepository.save(estagiario);
        return toResponseDTO(estagiarioSalvo);
    }

    public List<EstagiarioResponseDTO> listarTodos() {
        return estagiarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EstagiarioResponseDTO buscarPorId(Long id, Authentication authentication) {
        Estagiario estagiario = buscarEntidadePorId(id);
        checkOwnership(estagiario, authentication);
        return toResponseDTO(estagiario);
    }

    public Estagiario buscarEntidadePorId(Long id) {
        return estagiarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado com ID: " + id));
    }

    @Transactional
    public EstagiarioResponseDTO atualizar(Long id, EstagiarioDTO dto, Authentication authentication) {
        Estagiario estagiario = buscarEntidadePorId(id);
        checkOwnership(estagiario, authentication);

        estagiario.setNome(dto.getNome());
        estagiario.setDataNascimento(dto.getDataNascimento());
        estagiario.setGenero(dto.getGenero());
        estagiario.setTelefone(dto.getTelefone());

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            estagiario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        if (dto.getEndereco() != null) {
            Endereco endereco = estagiario.getEndereco();
            endereco.setLogradouro(dto.getEndereco().getLogradouro());
            endereco.setBairro(dto.getEndereco().getBairro());
            endereco.setCidade(dto.getEndereco().getCidade());
            endereco.setNumero(dto.getEndereco().getNumero());
            endereco.setEstados(dto.getEndereco().getEstados());
            endereco.setCep(dto.getEndereco().getCep());
        }

        Estagiario estagiarioAtualizado = estagiarioRepository.save(estagiario);
        return toResponseDTO(estagiarioAtualizado);
    }

    @Transactional
    public void excluir(Long id, Authentication authentication) {
        Estagiario estagiario = buscarEntidadePorId(id);
        checkOwnership(estagiario, authentication);
        estagiarioRepository.delete(estagiario);
    }

    private void checkOwnership(Estagiario estagiario, Authentication authentication) {
        String authEmail = authentication.getName();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return;
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"))) {
            if (estagiario.getEmail().equals(authEmail)) {
                return;
            }
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GERENTE"))) {
            if (Objects.equals(estagiario.getEmpresa().getId(), estagiario.getEmpresa().getId())) {
                return;
            }
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_FACULDADE"))) {
            Coordenador coordenador = coordenadorRepository.findByEmail(authEmail)
                    .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado."));

            if (Objects.equals(coordenador.getFaculdade().getId(), estagiario.getDadosAcademicos().getFaculdade().getId())) {
                return;
            }
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

        if (estagiario.getEmpresa() != null) {
            EmpresaResponseDTO empresaDTO = new EmpresaResponseDTO();
            empresaDTO.setId(estagiario.getEmpresa().getId());
            empresaDTO.setNome(estagiario.getEmpresa().getNome());
            empresaDTO.setCnpj(estagiario.getEmpresa().getCnpj());
            empresaDTO.setCodigoEmpresa(estagiario.getEmpresa().getCodigoEmpresa());
            dto.setEmpresa(empresaDTO);
        }

        if (estagiario.getEndereco() != null) {
            dto.setEndereco(toEnderecoDTO(estagiario.getEndereco()));
        }

        if (estagiario.getDadosAcademicos() != null) {
            dto.setDadosAcademicos(toDadosAcademicosResponseDTO(estagiario.getDadosAcademicos()));
        }

        return dto;
    }

    private DadosAcademicosResponseDTO toDadosAcademicosResponseDTO(DadosAcademicos dados) {
        if (dados == null) return null;

        DadosAcademicosResponseDTO dto = new DadosAcademicosResponseDTO();
        dto.setId(dados.getId());
        dto.setCurso(dados.getCurso());
        dto.setPeriodoSemestre(dados.getPeriodoSemestre());
        dto.setPrevisaoFormatura(dados.getPrevisaoFormatura().toString());
        dto.setRa(dados.getRa());

        if (dados.getFaculdade() != null) {
            FaculdadeResponseDTO faculdadeDTO = new FaculdadeResponseDTO();
            faculdadeDTO.setId(dados.getFaculdade().getId());
            faculdadeDTO.setNome(dados.getFaculdade().getNome());
            faculdadeDTO.setCnpj(dados.getFaculdade().getCnpj());
            dto.setFaculdade(faculdadeDTO);
        }
        return dto;
    }

    private EnderecoDTO toEnderecoDTO(Endereco endereco) {
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