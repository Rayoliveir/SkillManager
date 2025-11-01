package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.DashboardEstagiarioDTO;
import com.senai.skillmanager.dto.EstagiarioResponseDTO;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final SupervisorRepository supervisorRepository;
    private final CoordenadorRepository coordenadorRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final EstagiarioService estagiarioService;
    private final AvaliacaoService avaliacaoService;

    public DashboardService(SupervisorRepository supervisorRepository,
                            CoordenadorRepository coordenadorRepository,
                            EstagiarioRepository estagiarioRepository,
                            EstagiarioService estagiarioService,
                            AvaliacaoService avaliacaoService) {
        this.supervisorRepository = supervisorRepository;
        this.coordenadorRepository = coordenadorRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.estagiarioService = estagiarioService;
        this.avaliacaoService = avaliacaoService;
    }

    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<EstagiarioResponseDTO> getSupervisorDashboardData() {
        String email = getAuthenticatedUserEmail();
        Supervisor supervisor = supervisorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Supervisor não encontrado."));

        Long empresaId = supervisor.getEmpresa().getId();
        return estagiarioRepository.findByEmpresaId(empresaId).stream()
                .map(estagiarioService::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EstagiarioResponseDTO> getFaculdadeDashboardData() {
        String email = getAuthenticatedUserEmail();

        // --- LÓGICA CORRIGIDA ---
        Coordenador coordenador = coordenadorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Coordenador não encontrado."));

        Long faculdadeId = coordenador.getFaculdade().getId(); // Pega o ID da instituição
        // -------------------------

        // A sua query original de busca de estagiários
        return estagiarioRepository.findByDadosAcademicos_Faculdade_Id(faculdadeId).stream()
                .map(estagiarioService::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DashboardEstagiarioDTO getEstagiarioDashboardData() {
        String email = getAuthenticatedUserEmail();
        Long estagiarioId = estagiarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Estagiário não encontrado."))
                .getId();

        DashboardEstagiarioDTO dashboardData = new DashboardEstagiarioDTO();
        dashboardData.setDadosEstagiario(estagiarioService.buscarPorId(estagiarioId));
        dashboardData.setAvaliacoes(avaliacaoService.listarPorEstagiario(estagiarioId));

        return dashboardData;
    }
}