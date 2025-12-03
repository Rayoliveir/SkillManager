package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.DashboardResponseDTO;
import com.senai.skillmanager.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DashboardResponseDTO> getDashboardData(Authentication authentication) {
        DashboardResponseDTO dashboardData = dashboardService.getDashboardData(authentication);
        return ResponseEntity.ok(dashboardData);
    }
}