package com.senai.skillmanager.controller;

import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import com.senai.skillmanager.service.CoordenadorService;
import com.senai.skillmanager.service.EstagiarioService;
import com.senai.skillmanager.service.SupervisorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final EstagiarioRepository estagiarioRepository;
    private final EstagiarioService estagiarioService;
    private final SupervisorRepository supervisorRepository;
    private final SupervisorService supervisorService;
    private final CoordenadorRepository coordenadorRepository;
    private final CoordenadorService coordenadorService;

    public DashboardController(EstagiarioRepository estagiarioRepository,
                               EstagiarioService estagiarioService,
                               SupervisorRepository supervisorRepository,
                               SupervisorService supervisorService,
                               CoordenadorRepository coordenadorRepository,
                               CoordenadorService coordenadorService) {
        this.estagiarioRepository = estagiarioRepository;
        this.estagiarioService = estagiarioService;
        this.supervisorRepository = supervisorRepository;
        this.supervisorService = supervisorService;
        this.coordenadorRepository = coordenadorRepository;
        this.coordenadorService = coordenadorService;
    }

    @GetMapping
    public ResponseEntity<?> getDashboardData(Authentication authentication) {
        String email = authentication.getName();
        System.out.println("🔍 [DASHBOARD] Tentando carregar dados para: " + email);

        try {
            // 1. Tenta encontrar como Estagiário
            Optional<Estagiario> estagiario = estagiarioRepository.findByEmail(email);
            if (estagiario.isPresent()) {
                System.out.println("✅ [DASHBOARD] Perfil encontrado: ESTAGIARIO");
                Map<String, Object> response = new HashMap<>();
                response.put("role", "ESTAGIARIO");
                response.put("dashboardEstagiario", estagiarioService.toResponseDTO(estagiario.get()));
                return ResponseEntity.ok(response);
            }

            // 2. Tenta encontrar como Supervisor
            Optional<Supervisor> supervisor = supervisorRepository.findByEmail(email);
            if (supervisor.isPresent()) {
                System.out.println("✅ [DASHBOARD] Perfil encontrado: SUPERVISOR");
                Map<String, Object> response = new HashMap<>();
                response.put("role", "SUPERVISOR");

                Map<String, Object> dashSupervisor = new HashMap<>();
                dashSupervisor.put("dadosSupervisor", supervisorService.toResponseDTO(supervisor.get()));

                // Busca estagiários da empresa
                Long empresaId = supervisor.get().getEmpresa().getId();
                dashSupervisor.put("estagiarios", estagiarioRepository.findByEmpresaId(empresaId)
                        .stream().map(estagiarioService::toResponseDTO).toList());

                // Métricas simples
                dashSupervisor.put("totalEstagiarios", estagiarioRepository.findByEmpresaId(empresaId).size());
                dashSupervisor.put("totalAvaliacoes", 0); // Implementar contagem real se necessário

                response.put("dashboardSupervisor", dashSupervisor);
                return ResponseEntity.ok(response);
            }

            // 3. Tenta encontrar como Coordenador
            Optional<Coordenador> coordenador = coordenadorRepository.findByEmail(email);
            if (coordenador.isPresent()) {
                System.out.println("✅ [DASHBOARD] Perfil encontrado: COORDENADOR");
                Map<String, Object> response = new HashMap<>();
                response.put("role", "COORDENADOR");

                Map<String, Object> dashCoordenador = new HashMap<>();
                dashCoordenador.put("dadosCoordenador", coordenadorService.toResponseDTO(coordenador.get()));
                dashCoordenador.put("dadosFaculdade", coordenadorService.toFaculdadeResponseDTO(coordenador.get().getFaculdade()));
                dashCoordenador.put("estagiarios", Collections.emptyList()); // Lista vazia por enquanto para não quebrar

                response.put("dashboardCoordenador", dashCoordenador);
                return ResponseEntity.ok(response);
            }

            System.out.println("❌ [DASHBOARD] ERRO: Usuário autenticado mas não encontrado nas tabelas.");
            return ResponseEntity.badRequest().body("Usuário não encontrado nas tabelas de perfil.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("🔥 [DASHBOARD] EXCEPTION: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Erro interno: " + e.getMessage());
        }
    }
}