package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.DashboardEstagiarioDTO;
import com.senai.skillmanager.dto.EstagiarioResponseDTO;
import com.senai.skillmanager.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GERENTE')")
    public ResponseEntity<List<EstagiarioResponseDTO>> getSupervisorDashboard(Authentication authentication) {
        return ResponseEntity.ok(dashboardService.getSupervisorDashboardData(authentication));
    }

    @GetMapping("/faculdade")
    @PreAuthorize("hasRole('FACULDADE')")
    public ResponseEntity<List<DashboardEstagiarioDTO>> getFaculdadeDashboard(Authentication authentication) {
        return ResponseEntity.ok(dashboardService.getFaculdadeDashboardData(authentication));
    }

    @GetMapping("/estagiario")
    @PreAuthorize("hasRole('ESTAGIARIO')")
    public ResponseEntity<DashboardEstagiarioDTO> getEstagiarioDashboard(Authentication authentication) {
        return ResponseEntity.ok(dashboardService.getEstagiarioDashboardData(authentication));
    }
}