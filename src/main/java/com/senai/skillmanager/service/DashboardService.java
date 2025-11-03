package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.AvaliacaoResponseDTO;
import com.senai.skillmanager.dto.DashboardEstagiarioDTO;
import com.senai.skillmanager.dto.EstagiarioResponseDTO;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
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

    public List<EstagiarioResponseDTO> getSupervisorDashboardData(Authentication authentication) {
        String email = authentication.getName();
        Supervisor supervisor = supervisorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado."));

        Long empresaId = supervisor.getEmpresa().getId();
        return estagiarioRepository.findByEmpresaId(empresaId).stream()
                .map(estagiarioService::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<DashboardEstagiarioDTO> getFaculdadeDashboardData(Authentication authentication) {
        String email = authentication.getName();

        Coordenador coordenador = coordenadorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado."));

        Long faculdadeId = coordenador.getFaculdade().getId();

        List<Estagiario> estagiariosDaFaculdade = estagiarioRepository.findByDadosAcademicos_Faculdade_Id(faculdadeId);

        return estagiariosDaFaculdade.stream()
                .map(estagiario -> buildEstagiarioDashboard(estagiario.getId(), authentication))
                .collect(Collectors.toList());
    }

    public DashboardEstagiarioDTO getEstagiarioDashboardData(Authentication authentication) {
        String email = authentication.getName();
        Long estagiarioId = estagiarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado."))
                .getId();

        return buildEstagiarioDashboard(estagiarioId, authentication);
    }

    private DashboardEstagiarioDTO buildEstagiarioDashboard(Long estagiarioId, Authentication authentication) {
        EstagiarioResponseDTO estagiarioDTO = estagiarioService.buscarPorId(estagiarioId, authentication);
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.listarPorEstagiario(estagiarioId);

        DashboardEstagiarioDTO dashboardData = new DashboardEstagiarioDTO();
        dashboardData.setDadosEstagiario(estagiarioDTO);
        dashboardData.setAvaliacoes(avaliacoes);

        return dashboardData;
    }
}