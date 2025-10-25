package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.DashboardEstagiarioDTO;
import com.senai.skillmanager.dto.EstagiarioResponseDTO;
import com.senai.skillmanager.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/supervisor")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GERENTE')") // <-- CORRIGIDO
    public ResponseEntity<List<EstagiarioResponseDTO>> getSupervisorDashboard() {
        return ResponseEntity.ok(dashboardService.getSupervisorDashboardData());
    }

    @GetMapping("/faculdade")
    @PreAuthorize("hasRole('FACULDADE')")
    public ResponseEntity<List<EstagiarioResponseDTO>> getFaculdadeDashboard() {
        return ResponseEntity.ok(dashboardService.getFaculdadeDashboardData());
    }

    @GetMapping("/estagiario")
    @PreAuthorize("hasRole('ESTAGIARIO')")
    public ResponseEntity<DashboardEstagiarioDTO> getEstagiarioDashboard() {
        return ResponseEntity.ok(dashboardService.getEstagiarioDashboardData());
    }
}