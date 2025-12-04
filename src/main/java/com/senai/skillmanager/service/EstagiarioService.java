package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.*;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.estagiario.DadosEstagio;
import com.senai.skillmanager.model.estagiario.DadosAcademicos;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.avaliacao.Avaliacao;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.repository.*;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstagiarioService {

    private final EstagiarioRepository estagiarioRepository;
    private final EmpresaService empresaService;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoService enderecoService;
    private final PasswordEncoder passwordEncoder;
    private final DadosEstagioRepository dadosEstagioRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final SupervisorService supervisorService;
    private final SupervisorRepository supervisorRepository;
    private final CoordenadorRepository coordenadorRepository;
    private final FaculdadeRepository faculdadeRepository;

    public EstagiarioService(EstagiarioRepository estagiarioRepository,
                             EmpresaService empresaService,
                             EnderecoRepository enderecoRepository,
                             EnderecoService enderecoService,
                             PasswordEncoder passwordEncoder,
                             DadosEstagioRepository dadosEstagioRepository,
                             AvaliacaoRepository avaliacaoRepository,
                             @Lazy SupervisorService supervisorService,
                             SupervisorRepository supervisorRepository,
                             CoordenadorRepository coordenadorRepository,
                             FaculdadeRepository faculdadeRepository) {
        this.estagiarioRepository = estagiarioRepository;
        this.empresaService = empresaService;
        this.enderecoRepository = enderecoRepository;
        this.enderecoService = enderecoService;
        this.passwordEncoder = passwordEncoder;
        this.dadosEstagioRepository = dadosEstagioRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.supervisorService = supervisorService;
        this.supervisorRepository = supervisorRepository;
        this.coordenadorRepository = coordenadorRepository;
        this.faculdadeRepository = faculdadeRepository;
    }

    @Transactional
    public EstagiarioResponseDTO salvar(EstagiarioDTO dto) {
        if (dto.getSenha() == null || dto.getSenha().isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória para o cadastro.");
        }

        Estagiario estagiario = new Estagiario();
        estagiario.setNome(dto.getNome());
        estagiario.setEmail(dto.getEmail());
        estagiario.setSenha(passwordEncoder.encode(dto.getSenha()));
        estagiario.setCpf(dto.getCpf());
        estagiario.setTelefone(dto.getTelefone());
        estagiario.setDataNascimento(dto.getDataNascimento());
        estagiario.setGenero(dto.getGenero());

        estagiario.setEmpresa(empresaService.buscarPorCodigo(dto.getCodigoEmpresa()));

        if (dto.getEndereco() != null) {
            Endereco endereco = enderecoService.toEntity(dto.getEndereco());
            estagiario.setEndereco(enderecoRepository.save(endereco));
        }

        if (dto.getDadosAcademicos() != null) {
            DadosAcademicos dados = new DadosAcademicos();
            dados.setCurso(dto.getDadosAcademicos().getCurso());
            dados.setPeriodoSemestre(dto.getDadosAcademicos().getPeriodoSemestre());
            dados.setRa(dto.getDadosAcademicos().getRa());

            if (dto.getDadosAcademicos().getPrevisaoFormatura() != null) {
                dados.setPrevisaoFormatura(YearMonth.parse(dto.getDadosAcademicos().getPrevisaoFormatura()));
            }

            Faculdade faculdade = faculdadeRepository.findByCnpj(dto.getDadosAcademicos().getFaculdadeCnpj())
                    .orElseThrow(() -> new EntityNotFoundException("Faculdade não encontrada com CNPJ: " + dto.getDadosAcademicos().getFaculdadeCnpj()));

            dados.setFaculdade(faculdade);
            estagiario.setDadosAcademicos(dados);
        } else {
            throw new IllegalArgumentException("Dados acadêmicos são obrigatórios.");
        }

        Estagiario salvo = estagiarioRepository.save(estagiario);
        return toResponseDTO(salvo);
    }

    public List<EstagiarioResponseDTO> listarTodos() {
        return estagiarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- ATUALIZADO PARA RECEBER AUTHENTICATION ---
    public EstagiarioResponseDTO buscarPorId(Long id, Authentication authentication) {
        Estagiario estagiario = estagiarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado com ID: " + id));

        checkOwnership(estagiario, authentication);

        return toResponseDTO(estagiario);
    }

    @Transactional
    public EstagiarioResponseDTO atualizar(Long id, EstagiarioDTO dto, Authentication authentication) {
        Estagiario estagiario = estagiarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado."));

        checkOwnership(estagiario, authentication);

        estagiario.setNome(dto.getNome());
        estagiario.setEmail(dto.getEmail());
        estagiario.setTelefone(dto.getTelefone());
        if (dto.getGenero() != null) {
            estagiario.setGenero(dto.getGenero());
        }

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            estagiario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        if (dto.getEndereco() != null) {
            if (estagiario.getEndereco() == null) {
                estagiario.setEndereco(enderecoService.toEntity(dto.getEndereco()));
            } else {
                Endereco end = estagiario.getEndereco();
                end.setLogradouro(dto.getEndereco().getLogradouro());
                end.setBairro(dto.getEndereco().getBairro());
                end.setCidade(dto.getEndereco().getCidade());
                end.setNumero(dto.getEndereco().getNumero());
                end.setEstados(dto.getEndereco().getEstados());
                end.setCep(dto.getEndereco().getCep());
            }
        }

        Estagiario atualizado = estagiarioRepository.save(estagiario);
        return toResponseDTO(atualizado);
    }

    @Transactional
    public void excluir(Long id, Authentication authentication) {
        Estagiario estagiario = estagiarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado."));

        checkOwnership(estagiario, authentication);

        estagiarioRepository.deleteById(id);
    }

    private void checkOwnership(Estagiario estagiario, Authentication authentication) {
        String authEmail = authentication.getName();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return;
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"))) {
            if (!estagiario.getEmail().equals(authEmail)) {
                throw new SecurityException("Acesso negado: Você não pode acessar dados de outros estagiários.");
            }
            return;
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GERENTE"))) {

            Supervisor supervisor = supervisorRepository.findByEmail(authEmail)
                    .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado."));

            if (!Objects.equals(supervisor.getEmpresa().getId(), estagiario.getEmpresa().getId())) {
                throw new SecurityException("Acesso negado: Este estagiário pertence a outra empresa.");
            }
            return;
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_FACULDADE"))) {
            Coordenador coordenador = coordenadorRepository.findByEmail(authEmail)
                    .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado."));

            if (estagiario.getDadosAcademicos() == null ||
                    estagiario.getDadosAcademicos().getFaculdade() == null ||
                    !Objects.equals(coordenador.getFaculdade().getId(), estagiario.getDadosAcademicos().getFaculdade().getId())) {
                throw new SecurityException("Acesso negado: Este estagiário não pertence à sua instituição de ensino.");
            }
            return;
        }

        throw new SecurityException("Acesso negado.");
    }

    public EstagiarioResponseDTO toResponseDTO(Estagiario entity) {
        if (entity == null) return null; // Proteção extra

        EstagiarioResponseDTO dto = new EstagiarioResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setCpf(entity.getCpf());
        dto.setTelefone(entity.getTelefone());
        dto.setDataNascimento(entity.getDataNascimento());
        dto.setGenero(entity.getGenero());

        if (entity.getEmpresa() != null) {
            dto.setEmpresa(empresaService.toResponseDTO(entity.getEmpresa()));
        }

        if (entity.getEndereco() != null) {
            dto.setEndereco(enderecoService.toResponseDTO(entity.getEndereco()));
        }

        if (entity.getDadosAcademicos() != null) {
            DadosAcademicosResponseDTO acadDto = new DadosAcademicosResponseDTO();
            acadDto.setId(entity.getDadosAcademicos().getId());
            acadDto.setRa(entity.getDadosAcademicos().getRa());
            acadDto.setCurso(entity.getDadosAcademicos().getCurso());

            try {
                String cleanStr = entity.getDadosAcademicos().getPeriodoSemestre().replaceAll("\\D+", "");
                acadDto.setPeriodoSemestre(cleanStr.isEmpty() ? 0 : Integer.parseInt(cleanStr));
            } catch (Exception e) {
                acadDto.setPeriodoSemestre(0);
            }

            if (entity.getDadosAcademicos().getPrevisaoFormatura() != null) {
                acadDto.setPrevisaoFormatura(entity.getDadosAcademicos().getPrevisaoFormatura().toString());
            }

            if (entity.getDadosAcademicos().getFaculdade() != null) {
                acadDto.setFaculdade(toFaculdadeResponseDTO(entity.getDadosAcademicos().getFaculdade()));
            }

            dto.setDadosAcademicos(acadDto);
        }

        // --- DADOS DE ESTÁGIO (Blindagem) ---
        Optional<DadosEstagio> dadosEstagio = dadosEstagioRepository.findByEstagiarioId(entity.getId());
        if (dadosEstagio.isPresent()) {
            DadosEstagio d = dadosEstagio.get();
            DadosEstagioResponseDTO dadosDTO = new DadosEstagioResponseDTO();
            dadosDTO.setId(d.getId());
            dadosDTO.setTitulo(d.getTitulo());
            dadosDTO.setDataInicio(d.getDataInicio());
            dadosDTO.setDataTermino(d.getDataTermino());
            dadosDTO.setCargaHoraria(d.getCargaHoraria());
            dadosDTO.setTipoEstagio(d.getTipoEstagio());
            dadosDTO.setTipoRemuneracao(d.getTipoRemuneracao());
            dadosDTO.setObservacoes(d.getObservacoes());
            dto.setDadosEstagio(dadosDTO);
        }

        // --- AVALIAÇÕES (Blindagem: nunca retorna null) ---
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByEstagiario_Id(entity.getId());
        if (avaliacoes != null && !avaliacoes.isEmpty()) {
            List<AvaliacaoResponseDTO> avaliacoesDTO = avaliacoes.stream().map(av -> {
                AvaliacaoResponseDTO avDto = new AvaliacaoResponseDTO();
                avDto.setId(av.getId());
                avDto.setTitulo(av.getTitulo());
                avDto.setFeedback(av.getFeedback());
                avDto.setNotaDesempenho(av.getNotaDesempenho());
                avDto.setNotaHabilidadesTecnicas(av.getNotaHabilidadesTecnicas());
                avDto.setNotaHabilidadesComportamentais(av.getNotaHabilidadesComportamentais());
                avDto.setDataAvaliacao(av.getDataAvaliacao());
                if (av.getSupervisor() != null) {
                    avDto.setSupervisor(supervisorService.toResponseDTO(av.getSupervisor()));
                }
                return avDto;
            }).collect(Collectors.toList());
            dto.setAvaliacoes(avaliacoesDTO);
        } else {
            dto.setAvaliacoes(new java.util.ArrayList<>()); // Lista Vazia em vez de null
        }

        return dto;
    }

    private FaculdadeResponseDTO toFaculdadeResponseDTO(Faculdade faculdade) {
        if (faculdade == null) return null;
        FaculdadeResponseDTO response = new FaculdadeResponseDTO();
        response.setId(faculdade.getId());
        response.setNome(faculdade.getNome());
        response.setCnpj(faculdade.getCnpj());
        response.setTelefone(faculdade.getTelefone());
        response.setSite(faculdade.getSite());
        return response;
    }
}