package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.DashboardResponseDTO;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        // Estrutura de resposta flexível
        Map<String, Object> response = new HashMap<>();

        // Tenta encontrar como Estagiário
        Optional<Estagiario> estagiario = estagiarioRepository.findByEmail(email);
        if (estagiario.isPresent()) {
            response.put("role", "ESTAGIARIO");
            response.put("dashboardEstagiario", estagiarioService.toResponseDTO(estagiario.get()));
            // Adiciona dados extras específicos se necessário (ex: competencias)
            // A lógica de carregar avaliações/competências já está dentro do toResponseDTO do Service
            return ResponseEntity.ok(response);
        }

        // Tenta encontrar como Supervisor
        Optional<Supervisor> supervisor = supervisorRepository.findByEmail(email);
        if (supervisor.isPresent()) {
            response.put("role", "SUPERVISOR");
            // Monta o objeto Dashboard do Supervisor
            Map<String, Object> dashSupervisor = new HashMap<>();
            dashSupervisor.put("dadosSupervisor", supervisorService.toResponseDTO(supervisor.get()));
            // Carrega a lista de estagiários desta empresa
            dashSupervisor.put("estagiarios", estagiarioRepository.findByEmpresaId(supervisor.get().getEmpresa().getId())
                    .stream().map(estagiarioService::toResponseDTO).toList());

            response.put("dashboardSupervisor", dashSupervisor);
            return ResponseEntity.ok(response);
        }

        // Tenta encontrar como Coordenador
        Optional<Coordenador> coordenador = coordenadorRepository.findByEmail(email);
        if (coordenador.isPresent()) {
            response.put("role", "COORDENADOR"); // ou FACULDADE
            // Monta o objeto Dashboard do Coordenador
            Map<String, Object> dashCoordenador = new HashMap<>();
            dashCoordenador.put("dadosCoordenador", coordenadorService.toResponseDTO(coordenador.get()));
            dashCoordenador.put("dadosFaculdade", coordenadorService.toFaculdadeResponseDTO(coordenador.get().getFaculdade()));

            // Carrega estagiários desta faculdade (precisa de um método no repository ou filtro)
            // Aqui vamos assumir uma busca simples por enquanto ou filtrar na memória se não tiver método customizado
            // Idealmente: estagiarioRepository.findByFaculdadeId(...)
            // Como paliativo, enviamos lista vazia ou implementamos a busca:

            // dashCoordenador.put("estagiarios", ...);

            response.put("dashboardCoordenador", dashCoordenador);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body("Usuário não encontrado ou sem perfil definido.");
    }
}