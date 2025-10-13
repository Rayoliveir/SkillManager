package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.DashboardEstagiarioDTO;
import com.senai.skillmanager.dto.EstagiarioResponseDTO;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.model.funcionario.Funcionario;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.FaculdadeRepository;
import com.senai.skillmanager.repository.FuncionarioRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final FuncionarioRepository funcionarioRepository;
    private final FaculdadeRepository faculdadeRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final EstagiarioService estagiarioService;
    private final AvaliacaoService avaliacaoService;

    public DashboardService(FuncionarioRepository funcionarioRepository, FaculdadeRepository faculdadeRepository, EstagiarioRepository estagiarioRepository, EstagiarioService estagiarioService, AvaliacaoService avaliacaoService) {
        this.funcionarioRepository = funcionarioRepository;
        this.faculdadeRepository = faculdadeRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.estagiarioService = estagiarioService;
        this.avaliacaoService = avaliacaoService;
    }

    private String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<EstagiarioResponseDTO> getSupervisorDashboardData() {
        String email = getAuthenticatedUserEmail();
        Funcionario supervisor = funcionarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Supervisor não encontrado."));

        Long empresaId = supervisor.getEmpresa().getId();
        return estagiarioRepository.findByEmpresaId(empresaId).stream()
                .map(estagiarioService::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EstagiarioResponseDTO> getFaculdadeDashboardData() {
        String email = getAuthenticatedUserEmail();
        Faculdade faculdade = faculdadeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Faculdade não encontrada."));

        Long faculdadeId = faculdade.getId();
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