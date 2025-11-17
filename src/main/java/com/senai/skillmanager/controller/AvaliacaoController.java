package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.AvaliacaoDTO;
import com.senai.skillmanager.dto.AvaliacaoResponseDTO;
import com.senai.skillmanager.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'SUPERVISOR')")
    public ResponseEntity<AvaliacaoResponseDTO> salvar(@Valid @RequestBody AvaliacaoDTO dto, Authentication authentication) {
        AvaliacaoResponseDTO response = avaliacaoService.salvar(dto, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarTodos() {
        List<AvaliacaoResponseDTO> response = avaliacaoService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estagiario/{estagiarioId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorEstagiario(@PathVariable Long estagiarioId, Authentication authentication) {
        List<AvaliacaoResponseDTO> response = avaliacaoService.listarPorEstagiario(estagiarioId, authentication);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AvaliacaoResponseDTO> buscarPorId(@PathVariable Long id, Authentication authentication) {
        AvaliacaoResponseDTO response = avaliacaoService.buscarPorId(id, authentication);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'SUPERVISOR')")
    public ResponseEntity<AvaliacaoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AvaliacaoDTO dto, Authentication authentication) {
        AvaliacaoResponseDTO response = avaliacaoService.atualizar(id, dto, authentication);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'SUPERVISOR', 'ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id, Authentication authentication) {
        avaliacaoService.excluir(id, authentication);
        return ResponseEntity.noContent().build();
    }
}