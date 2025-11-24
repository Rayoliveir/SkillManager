package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.*;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public DashboardService(EstagiarioRepository estagiarioRepository,
                            SupervisorRepository supervisorRepository,
                            CoordenadorRepository coordenadorRepository,
                            AvaliacaoService avaliacaoService,
                            EstagiarioService estagiarioService,
                            @Lazy SupervisorService supervisorService,
                            @Lazy CoordenadorService coordenadorService) {
        this.estagiarioRepository = estagiarioRepository;
        this.supervisorRepository = supervisorRepository;
        this.coordenadorRepository = coordenadorRepository;
        this.avaliacaoService = avaliacaoService;
        this.estagiarioService = estagiarioService;
        this.supervisorService = supervisorService;
        this.coordenadorService = coordenadorService;
    }

    public DashboardResponseDTO getDashboardData(Authentication authentication) {
        String email = authentication.getName();
        DashboardResponseDTO response = new DashboardResponseDTO();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"))) {
            // --- Lógica do Estagiário (Mantida) ---
            Estagiario estagiario = estagiarioRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado."));

            List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.listarPorEstagiario(estagiario.getId(), authentication);

            DashboardEstagiarioDTO estagiarioDTO = new DashboardEstagiarioDTO();
            estagiarioDTO.setDadosEstagiario(estagiarioService.toResponseDTO(estagiario));
            estagiarioDTO.setAvaliacoes(avaliacoes);

            response.setDashboardEstagiario(estagiarioDTO);

        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GERENTE"))) {

            // --- Lógica do Supervisor (CORRIGIDA) ---
            Supervisor supervisor = supervisorRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado."));

            // 1. Busca os dados do supervisor
            SupervisorResponseDTO supervisorInfo = supervisorService.toResponseDTO(supervisor);

            // 2. Busca a lista de estagiários (Entidades) da empresa
            List<Estagiario> estagiariosDaEmpresa = estagiarioRepository.findByEmpresaId(supervisor.getEmpresa().getId());

            // Converte para DTOs
            List<EstagiarioResponseDTO> estagiariosDTO = estagiariosDaEmpresa.stream()
                    .map(estagiarioService::toResponseDTO)
                    .collect(Collectors.toList());

            DashboardSupervisorResponseDTO supervisorDashboard = new DashboardSupervisorResponseDTO();
            supervisorDashboard.setDadosSupervisor(supervisorInfo);
            supervisorDashboard.setEstagiarios(estagiariosDTO);

            // --- CORREÇÃO: Preencher os totais no Back-end ---

            // Total de Estagiários
            supervisorDashboard.setTotalEstagiarios(estagiariosDaEmpresa.size());

            // Total de Avaliações Feitas (Soma das avaliações de todos os estagiários da empresa)
            int totalAvaliacoes = 0;
            for (Estagiario estagiario : estagiariosDaEmpresa) {
                // Reutiliza o serviço que já busca as avaliações por estagiário
                List<AvaliacaoResponseDTO> avs = avaliacaoService.listarPorEstagiario(estagiario.getId(), authentication);
                totalAvaliacoes += avs.size();
            }
            supervisorDashboard.setTotalAvaliacoes(totalAvaliacoes);

            response.setDashboardSupervisor(supervisorDashboard);

        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_FACULDADE"))) {
            // --- Lógica da Faculdade (Mantida) ---
            Coordenador coordenador = coordenadorRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado."));

            FaculdadeResponseDTO faculdadeInfo = coordenadorService.toFaculdadeResponseDTO(coordenador.getFaculdade());
            List<Estagiario> estagiariosDaFaculdade = estagiarioRepository.findByDadosAcademicos_Faculdade_Id(coordenador.getFaculdade().getId());

            List<EstagiarioResponseDTO> estagiariosDTO = estagiariosDaFaculdade.stream()
                    .map(estagiarioService::toResponseDTO)
                    .collect(Collectors.toList());

            DashboardCoordenadorResponseDTO coordenadorDashboard = new DashboardCoordenadorResponseDTO();
            coordenadorDashboard.setDadosFaculdade(faculdadeInfo);
            coordenadorDashboard.setEstagiarios(estagiariosDTO);

            // Cálculos Faculdade
            coordenadorDashboard.setTotalEstagiarios(estagiariosDaFaculdade.size());

            long empresasUnicas = estagiariosDaFaculdade.stream()
                    .map(e -> e.getEmpresa().getId())
                    .distinct()
                    .count();
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
}