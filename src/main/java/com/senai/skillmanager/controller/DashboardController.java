package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.EstagiarioResponseDTO;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import com.senai.skillmanager.service.CoordenadorService;
import com.senai.skillmanager.service.EstagiarioService;
import com.senai.skillmanager.service.SupervisorService;
import org.springframework.http.HttpStatus;
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
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }

        String email = authentication.getName().toLowerCase();
        System.out.println("🔍 [DASHBOARD] Iniciando busca de dados para: " + email);

        try {
            // 1. Tenta encontrar como Estagiário
            Optional<Estagiario> estagiario = estagiarioRepository.findByEmail(email);
            if (estagiario.isEmpty()) {
                estagiario = estagiarioRepository.findByEmail(authentication.getName());
            }

            if (estagiario.isPresent()) {
                System.out.println("✅ [DASHBOARD] Perfil encontrado: ESTAGIARIO - ID: " + estagiario.get().getId());
                Map<String, Object> response = new HashMap<>();
                response.put("role", "ESTAGIARIO");

                // --- CORREÇÃO AQUI: Montando a estrutura que o Front espera ---
                Map<String, Object> dashEstagiario = new HashMap<>();
                EstagiarioResponseDTO dto = estagiarioService.toResponseDTO(estagiario.get());

                dashEstagiario.put("dadosEstagiario", dto); // O Front exige essa chave
                dashEstagiario.put("avaliacoes", dto.getAvaliacoes()); // Extrai avaliações para a raiz do objeto
                dashEstagiario.put("competencias", Collections.emptyList()); // Placeholder (evita erro undefined)
                dashEstagiario.put("conquistas", Collections.emptyList());   // Placeholder

                response.put("dashboardEstagiario", dashEstagiario);
                return ResponseEntity.ok(response);
                // -------------------------------------------------------------
            }

            // 2. Tenta encontrar como Supervisor
            Optional<Supervisor> supervisor = supervisorRepository.findByEmail(email);
            if (supervisor.isEmpty()) {
                supervisor = supervisorRepository.findByEmail(authentication.getName());
            }

            if (supervisor.isPresent()) {
                System.out.println("✅ [DASHBOARD] Perfil encontrado: SUPERVISOR - ID: " + supervisor.get().getId());
                Map<String, Object> response = new HashMap<>();
                response.put("role", "SUPERVISOR");

                Map<String, Object> dashSupervisor = new HashMap<>();
                dashSupervisor.put("dadosSupervisor", supervisorService.toResponseDTO(supervisor.get()));

                if (supervisor.get().getEmpresa() != null) {
                    Long empresaId = supervisor.get().getEmpresa().getId();
                    try {
                        dashSupervisor.put("estagiarios", estagiarioRepository.findByEmpresaId(empresaId)
                                .stream().map(estagiarioService::toResponseDTO).toList());
                        dashSupervisor.put("totalEstagiarios", estagiarioRepository.findByEmpresaId(empresaId).size());
                    } catch (Exception e) {
                        dashSupervisor.put("estagiarios", Collections.emptyList());
                        dashSupervisor.put("totalEstagiarios", 0);
                    }
                } else {
                    dashSupervisor.put("estagiarios", Collections.emptyList());
                    dashSupervisor.put("totalEstagiarios", 0);
                }

                dashSupervisor.put("totalAvaliacoes", 0);
                response.put("dashboardSupervisor", dashSupervisor);
                return ResponseEntity.ok(response);
            }

            // 3. Tenta encontrar como Coordenador
            Optional<Coordenador> coordenador = coordenadorRepository.findByEmail(email);
            if (coordenador.isEmpty()) {
                coordenador = coordenadorRepository.findByEmail(authentication.getName());
            }

            if (coordenador.isPresent()) {
                System.out.println("✅ [DASHBOARD] Perfil encontrado: COORDENADOR - ID: " + coordenador.get().getId());
                Map<String, Object> response = new HashMap<>();
                response.put("role", "COORDENADOR");

                Map<String, Object> dashCoordenador = new HashMap<>();
                dashCoordenador.put("dadosCoordenador", coordenadorService.toResponseDTO(coordenador.get()));

                if (coordenador.get().getFaculdade() != null) {
                    dashCoordenador.put("dadosFaculdade", coordenadorService.toFaculdadeResponseDTO(coordenador.get().getFaculdade()));
                    try {
                        Long faculdadeId = coordenador.get().getFaculdade().getId();
                        dashCoordenador.put("estagiarios", estagiarioRepository.findByDadosAcademicos_Faculdade_Id(faculdadeId)
                                .stream().map(estagiarioService::toResponseDTO).toList());
                        dashCoordenador.put("totalEstagiarios", estagiarioRepository.findByDadosAcademicos_Faculdade_Id(faculdadeId).size());
                    } catch (Exception e) {
                        dashCoordenador.put("estagiarios", Collections.emptyList());
                        dashCoordenador.put("totalEstagiarios", 0);
                    }
                } else {
                    dashCoordenador.put("estagiarios", Collections.emptyList());
                    dashCoordenador.put("totalEstagiarios", 0);
                }

                response.put("dashboardCoordenador", dashCoordenador);
                return ResponseEntity.ok(response);
            }

            System.out.println("❌ [DASHBOARD] ERRO CRÍTICO: Usuário " + email + " não encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil não encontrado.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }
}