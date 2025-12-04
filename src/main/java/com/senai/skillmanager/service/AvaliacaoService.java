package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.AvaliacaoDTO;
import com.senai.skillmanager.dto.AvaliacaoResponseDTO;
import com.senai.skillmanager.dto.DadosEstagioResponseDTO;
import com.senai.skillmanager.dto.EstagiarioResponseDTO;
import com.senai.skillmanager.model.avaliacao.Avaliacao;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.DadosEstagio;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final SupervisorRepository supervisorRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final CoordenadorRepository coordenadorRepository;
    private final DadosEstagioRepository dadosEstagioRepository; // Injeção Nova
    private final SupervisorService supervisorService;
    private final EstagiarioService estagiarioService;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository,
                            SupervisorRepository supervisorRepository,
                            EstagiarioRepository estagiarioRepository,
                            CoordenadorRepository coordenadorRepository,
                            DadosEstagioRepository dadosEstagioRepository, // Adicionado ao construtor
                            SupervisorService supervisorService,
                            EstagiarioService estagiarioService) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.supervisorRepository = supervisorRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.coordenadorRepository = coordenadorRepository;
        this.dadosEstagioRepository = dadosEstagioRepository; // Inicialização nova
        this.supervisorService = supervisorService;
        this.estagiarioService = estagiarioService;
    }

    @Transactional
    public AvaliacaoResponseDTO salvar(AvaliacaoDTO dto, Authentication authentication) {
        Supervisor supervisor = getSupervisorFromAuth(authentication);
        Estagiario estagiario = getEstagiarioFromId(dto.getEstagiarioId());

        checkSupervisorEstagiarioEmpresa(supervisor, estagiario);

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setTitulo(dto.getTitulo());
        avaliacao.setFeedback(dto.getFeedback());
        avaliacao.setNotaDesempenho(dto.getNotaDesempenho());
        avaliacao.setNotaHabilidadesTecnicas(dto.getNotaHabilidadesTecnicas());
        avaliacao.setNotaHabilidadesComportamentais(dto.getNotaHabilidadesComportamentais());
        avaliacao.setDataAvaliacao(LocalDate.now());
        avaliacao.setSupervisor(supervisor);
        avaliacao.setEstagiario(estagiario);

        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);
        return toResponseDTO(avaliacaoSalva);
    }

    public List<AvaliacaoResponseDTO> listarTodos() {
        return avaliacaoRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<AvaliacaoResponseDTO> listarPorEstagiario(Long estagiarioId, Authentication authentication) {
        Estagiario estagiario = getEstagiarioFromId(estagiarioId);

        checkOwnership(null, estagiario, authentication);

        List<Avaliacao> avaliacoes = avaliacaoRepository.findByEstagiario_Id(estagiarioId);
        return avaliacoes.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AvaliacaoResponseDTO buscarPorId(Long id, Authentication authentication) {
        Avaliacao avaliacao = buscarEntidadePorId(id);
        checkOwnership(avaliacao, avaliacao.getEstagiario(), authentication);
        return toResponseDTO(avaliacao);
    }

    @Transactional
    public AvaliacaoResponseDTO atualizar(Long id, AvaliacaoDTO dto, Authentication authentication) {
        Supervisor supervisor = getSupervisorFromAuth(authentication);
        Estagiario estagiario = getEstagiarioFromId(dto.getEstagiarioId());
        Avaliacao avaliacao = buscarEntidadePorId(id);

        checkOwnership(avaliacao, estagiario, authentication);

        if (!Objects.equals(avaliacao.getSupervisor().getId(), supervisor.getId())) {
            throw new SecurityException("Acesso negado: Somente o supervisor que criou a avaliação pode atualizá-la.");
        }
        checkSupervisorEstagiarioEmpresa(supervisor, estagiario);

        avaliacao.setTitulo(dto.getTitulo());
        avaliacao.setFeedback(dto.getFeedback());
        avaliacao.setNotaDesempenho(dto.getNotaDesempenho());
        avaliacao.setNotaHabilidadesTecnicas(dto.getNotaHabilidadesTecnicas());
        avaliacao.setNotaHabilidadesComportamentais(dto.getNotaHabilidadesComportamentais());
        avaliacao.setEstagiario(estagiario);

        Avaliacao avaliacaoAtualizada = avaliacaoRepository.save(avaliacao);
        return toResponseDTO(avaliacaoAtualizada);
    }

    @Transactional
    public void excluir(Long id, Authentication authentication) {
        Avaliacao avaliacao = buscarEntidadePorId(id);
        checkOwnership(avaliacao, avaliacao.getEstagiario(), authentication);

        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            Supervisor supervisor = getSupervisorFromAuth(authentication);
            if (!Objects.equals(avaliacao.getSupervisor().getId(), supervisor.getId())) {
                throw new SecurityException("Acesso negado: Somente o supervisor que criou a avaliação ou um Admin pode excluí-la.");
            }
        }

        avaliacaoRepository.delete(avaliacao);
    }

    private Supervisor getSupervisorFromAuth(Authentication authentication) {
        String supervisorEmail = authentication.getName();
        return supervisorRepository.findByEmail(supervisorEmail)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado com email: " + supervisorEmail));
    }

    private Estagiario getEstagiarioFromId(Long estagiarioId) {
        return estagiarioRepository.findById(estagiarioId)
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado com ID: " + estagiarioId));
    }

    private Avaliacao buscarEntidadePorId(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avaliação não encontrada com ID: " + id));
    }

    private void checkSupervisorEstagiarioEmpresa(Supervisor supervisor, Estagiario estagiario) {
        if (!Objects.equals(estagiario.getEmpresa().getId(), supervisor.getEmpresa().getId())) {
            throw new SecurityException("Acesso negado: O estagiário não pertence à empresa deste supervisor.");
        }
    }

    private void checkOwnership(Avaliacao avaliacao, Estagiario estagiario, Authentication authentication) {
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

            if (avaliacao != null && avaliacao.getSupervisor().getEmail().equals(authEmail)) {
                return;
            }

            Supervisor supervisor = getSupervisorFromAuth(authentication);
            if (Objects.equals(supervisor.getEmpresa().getId(), estagiario.getEmpresa().getId())) {
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

    private AvaliacaoResponseDTO toResponseDTO(Avaliacao entity) {
        AvaliacaoResponseDTO dto = new AvaliacaoResponseDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setFeedback(entity.getFeedback());
        dto.setNotaDesempenho(entity.getNotaDesempenho());
        dto.setNotaHabilidadesTecnicas(entity.getNotaHabilidadesTecnicas());
        dto.setNotaHabilidadesComportamentais(entity.getNotaHabilidadesComportamentais());
        dto.setDataAvaliacao(entity.getDataAvaliacao());

        // Supervisor pode ir completo, geralmente não causa loop se Supervisor não listar avaliações
        dto.setSupervisor(supervisorService.toResponseDTO(entity.getSupervisor()));

        // --- CORREÇÃO DO LOOP INFINITO ---
        // Em vez de chamar estagiarioService.toResponseDTO(entity.getEstagiario())
        // (que buscaria as avaliações de novo), vamos montar um DTO manual "leve".

        EstagiarioResponseDTO estagiarioDTO = new EstagiarioResponseDTO();
        estagiarioDTO.setId(entity.getEstagiario().getId());
        estagiarioDTO.setNome(entity.getEstagiario().getNome());
        estagiarioDTO.setEmail(entity.getEstagiario().getEmail());
        // Não preenchemos as avaliações nem dados pesados aqui dentro

        dto.setEstagiario(estagiarioDTO);
        // ---------------------------------

        return dto;
    }

    // Helper para converter DadosEstagio (mesma lógica do DashboardService)
    private DadosEstagioResponseDTO toDadosEstagioDTO(DadosEstagio entity) {
        if (entity == null) return null;
        DadosEstagioResponseDTO dto = new DadosEstagioResponseDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDataInicio(entity.getDataInicio());
        dto.setDataTermino(entity.getDataTermino());
        dto.setCargaHoraria(entity.getCargaHoraria());
        dto.setTipoEstagio(entity.getTipoEstagio());
        // Se precisar retornar o TipoRemuneracao no front, adicione aqui:
        // dto.setTipoRemuneracao(entity.getTipoRemuneracao());
        return dto;
    }
}