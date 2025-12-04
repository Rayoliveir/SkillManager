package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.*;
import com.senai.skillmanager.model.competencia.NivelCompetencia; // Importante para a nova lógica
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.DadosEstagio;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.DadosEstagioRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final EstagiarioRepository estagiarioRepository;
    private final SupervisorRepository supervisorRepository;
    private final CoordenadorRepository coordenadorRepository;
    private final AvaliacaoService avaliacaoService;
    private final EstagiarioService estagiarioService;
    private final SupervisorService supervisorService;
    private final CoordenadorService coordenadorService;
    private final CompetenciaService competenciaService;
    private final DadosEstagioRepository dadosEstagioRepository;

    public DashboardService(EstagiarioRepository estagiarioRepository,
                            SupervisorRepository supervisorRepository,
                            CoordenadorRepository coordenadorRepository,
                            AvaliacaoService avaliacaoService,
                            EstagiarioService estagiarioService,
                            @Lazy SupervisorService supervisorService,
                            @Lazy CoordenadorService coordenadorService,
                            CompetenciaService competenciaService,
                            DadosEstagioRepository dadosEstagioRepository) {
        this.estagiarioRepository = estagiarioRepository;
        this.supervisorRepository = supervisorRepository;
        this.coordenadorRepository = coordenadorRepository;
        this.avaliacaoService = avaliacaoService;
        this.estagiarioService = estagiarioService;
        this.supervisorService = supervisorService;
        this.coordenadorService = coordenadorService;
        this.competenciaService = competenciaService;
        this.dadosEstagioRepository = dadosEstagioRepository;
    }

    public DashboardResponseDTO getDashboardData(Authentication authentication) {
        String email = authentication.getName();
        DashboardResponseDTO response = new DashboardResponseDTO();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"))) {
            Estagiario estagiario = estagiarioRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado."));

            List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.listarPorEstagiario(estagiario.getId(), authentication);
            List<CompetenciaDTO> competencias = competenciaService.listarPorEstagiario(estagiario.getId());

            DashboardEstagiarioDTO estagiarioDTO = new DashboardEstagiarioDTO();
            estagiarioDTO.setDadosEstagiario(estagiarioService.toResponseDTO(estagiario));

            Optional<DadosEstagio> dados = dadosEstagioRepository.findByEstagiarioId(estagiario.getId());
            dados.ifPresent(d -> estagiarioDTO.getDadosEstagiario().setDadosEstagio(toDadosEstagioDTO(d)));

            estagiarioDTO.setAvaliacoes(avaliacoes);
            estagiarioDTO.setCompetencias(competencias);

            // --- GERA AS CONQUISTAS (NOVA LÓGICA) ---
            estagiarioDTO.setConquistas(gerarConquistas(estagiario, avaliacoes, competencias, dados.orElse(null)));

            response.setDashboardEstagiario(estagiarioDTO);

        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GERENTE"))) {

            Supervisor supervisor = supervisorRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado."));

            SupervisorResponseDTO supervisorInfo = supervisorService.toResponseDTO(supervisor);
            List<Estagiario> estagiariosDaEmpresa = estagiarioRepository.findByEmpresaId(supervisor.getEmpresa().getId());

            List<EstagiarioResponseDTO> estagiariosDTO = estagiariosDaEmpresa.stream()
                    .map(est -> {
                        EstagiarioResponseDTO dto = estagiarioService.toResponseDTO(est);
                        Optional<DadosEstagio> dados = dadosEstagioRepository.findByEstagiarioId(est.getId());
                        dados.ifPresent(d -> dto.setDadosEstagio(toDadosEstagioDTO(d)));
                        return dto;
                    })
                    .collect(Collectors.toList());

            DashboardSupervisorResponseDTO supervisorDashboard = new DashboardSupervisorResponseDTO();
            supervisorDashboard.setDadosSupervisor(supervisorInfo);
            supervisorDashboard.setEstagiarios(estagiariosDTO);
            supervisorDashboard.setTotalEstagiarios(estagiariosDaEmpresa.size());

            int totalAvaliacoes = 0;
            for (Estagiario estagiario : estagiariosDaEmpresa) {
                List<AvaliacaoResponseDTO> avs = avaliacaoService.listarPorEstagiario(estagiario.getId(), authentication);
                totalAvaliacoes += avs.size();
            }
            supervisorDashboard.setTotalAvaliacoes(totalAvaliacoes);

            response.setDashboardSupervisor(supervisorDashboard);

        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_FACULDADE"))) {

            Coordenador coordenador = coordenadorRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado."));

            FaculdadeResponseDTO faculdadeInfo = coordenadorService.toFaculdadeResponseDTO(coordenador.getFaculdade());
            CoordenadorResponseDTO coordenadorInfo = coordenadorService.toResponseDTO(coordenador);

            List<Estagiario> estagiariosDaFaculdade = estagiarioRepository.findByDadosAcademicos_Faculdade_Id(coordenador.getFaculdade().getId());

            List<EstagiarioResponseDTO> estagiariosDTO = estagiariosDaFaculdade.stream()
                    .map(estagiarioService::toResponseDTO)
                    .collect(Collectors.toList());

            DashboardCoordenadorResponseDTO coordenadorDashboard = new DashboardCoordenadorResponseDTO();
            coordenadorDashboard.setDadosFaculdade(faculdadeInfo);
            coordenadorDashboard.setDadosCoordenador(coordenadorInfo);
            coordenadorDashboard.setEstagiarios(estagiariosDTO);
            coordenadorDashboard.setTotalEstagiarios(estagiariosDaFaculdade.size());

            long empresasUnicas = estagiariosDaFaculdade.stream().map(e -> e.getEmpresa().getId()).distinct().count();
            coordenadorDashboard.setTotalEmpresasParceiras((int) empresasUnicas);

            int totalAvaliacoes = 0;
            double somaNotas = 0.0;
            for (Estagiario aluno : estagiariosDaFaculdade) {
                List<AvaliacaoResponseDTO> avaliacoesAluno = avaliacaoService.listarPorEstagiario(aluno.getId(), authentication);
                totalAvaliacoes += avaliacoesAluno.size();
                for (AvaliacaoResponseDTO av : avaliacoesAluno) {
                    double mediaAvaliacao = (av.getNotaDesempenho() + av.getNotaHabilidadesTecnicas() + av.getNotaHabilidadesComportamentais()) / 3.0;
                    somaNotas += mediaAvaliacao;
                }
            }
            coordenadorDashboard.setTotalAvaliacoesRecebidas(totalAvaliacoes);

            if (totalAvaliacoes > 0) {
                double mediaFinal = somaNotas / totalAvaliacoes;
                coordenadorDashboard.setMediaGeralNotas(Math.round(mediaFinal * 10.0) / 10.0);
            } else {
                coordenadorDashboard.setMediaGeralNotas(0.0);
            }

            response.setDashboardCoordenador(coordenadorDashboard);
        }

        return response;
    }

    // --- LÓGICA DE GAMIFICAÇÃO EXPANDIDA ---
    // --- LÓGICA DE GAMIFICAÇÃO (ATUALIZADA PARA LUCIDE ICONS) ---
    private List<ConquistaDTO> gerarConquistas(Estagiario estagiario, List<AvaliacaoResponseDTO> avaliacoes, List<CompetenciaDTO> competencias, DadosEstagio dadosEstagio) {
        List<ConquistaDTO> conquistas = new ArrayList<>();

        // 1. Bem-vindo
        conquistas.add(new ConquistaDTO("Bem-vindo a bordo", "Perfil criado com sucesso.", "Hand"));

        // 2. Perfil Completo
        if (estagiario.getEndereco() != null && estagiario.getEndereco().getCep() != null) {
            conquistas.add(new ConquistaDTO("Perfil completo", "Cadastro 100% preenchido.", "UserCheck"));
        }

        if (dadosEstagio != null) {
            conquistas.add(new ConquistaDTO("Profissional", "Contrato de estágio ativo.", "Briefcase"));

            long mesesDeCasa = ChronoUnit.MONTHS.between(dadosEstagio.getDataInicio(), LocalDate.now());
            if (mesesDeCasa >= 3) conquistas.add(new ConquistaDTO("Experiente", "3 meses de casa.", "Medal"));
            if (mesesDeCasa >= 6) conquistas.add(new ConquistaDTO("Veterano", "6 meses de dedicação.", "Award"));

            if (dadosEstagio.getDataTermino() != null) {
                long totalDias = ChronoUnit.DAYS.between(dadosEstagio.getDataInicio(), dadosEstagio.getDataTermino());
                long diasPercorridos = ChronoUnit.DAYS.between(dadosEstagio.getDataInicio(), LocalDate.now());
                if (diasPercorridos >= (totalDias / 2)) {
                    conquistas.add(new ConquistaDTO("Meio caminho", "50% do estágio concluído.", "Hourglass"));
                }
            }
        }

        if (!avaliacoes.isEmpty()) {
            conquistas.add(new ConquistaDTO("Primeiro feedback", "Recebeu a primeira avaliação.", "MessageSquare"));

            boolean comportamentalTop = avaliacoes.stream().anyMatch(a -> a.getNotaHabilidadesComportamentais() == 5);
            if (comportamentalTop) conquistas.add(new ConquistaDTO("Exemplar", "Nota máxima em Comportamento.", "Heart"));

            boolean tecnicaTop = avaliacoes.stream().anyMatch(a -> a.getNotaHabilidadesTecnicas() == 5);
            if (tecnicaTop) conquistas.add(new ConquistaDTO("Mago da Técnica", "Nota máxima em Técnica.", "Zap"));

            boolean temNotaAlta = avaliacoes.stream().anyMatch(a ->
                    (a.getNotaDesempenho() + a.getNotaHabilidadesTecnicas() + a.getNotaHabilidadesComportamentais()) / 3.0 >= 4.5
            );
            if (temNotaAlta) conquistas.add(new ConquistaDTO("Alta performance", "Média superior a 4.5.", "TrendingUp"));

            if (avaliacoes.size() >= 3) conquistas.add(new ConquistaDTO("Em evolução", "3+ feedbacks recebidos.", "Activity"));

            if (dadosEstagio != null && ChronoUnit.MONTHS.between(dadosEstagio.getDataInicio(), LocalDate.now()) >= 6 && temNotaAlta) {
                conquistas.add(new ConquistaDTO("Lenda do estágio", "6 meses + performance ótima.", "Crown"));
            }
        }

        if (competencias.size() >= 3) conquistas.add(new ConquistaDTO("Multitarefa", "3+ competências cadastradas.", "Layers"));

        boolean isEspecialista = competencias.stream().anyMatch(c ->
                c.getNivel() == NivelCompetencia.AVANCADO
        );
        if (isEspecialista) conquistas.add(new ConquistaDTO("Especialista", "Nível avançado em uma skill.", "Star"));

        return conquistas;
    }

    private DadosEstagioResponseDTO toDadosEstagioDTO(DadosEstagio entity) {
        if (entity == null) return null;
        DadosEstagioResponseDTO dto = new DadosEstagioResponseDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDataInicio(entity.getDataInicio());
        dto.setDataTermino(entity.getDataTermino());
        dto.setCargaHoraria(entity.getCargaHoraria());
        dto.setTipoEstagio(entity.getTipoEstagio());
        return dto;
    }
}