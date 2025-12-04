package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.CompetenciaDTO;
import com.senai.skillmanager.service.CompetenciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competencias")
@CrossOrigin(origins = "*")
public class CompetenciaController {

    private final CompetenciaService competenciaService;

    public CompetenciaController(CompetenciaService competenciaService) {
        this.competenciaService = competenciaService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'GERENTE', 'ADMIN')")
    public ResponseEntity<CompetenciaDTO> adicionar(@Valid @RequestBody CompetenciaDTO dto, Authentication authentication) {
        CompetenciaDTO novaCompetencia = competenciaService.adicionar(dto, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCompetencia);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'GERENTE', 'ADMIN')")
    public ResponseEntity<Void> remover(@PathVariable Long id, Authentication authentication) {
        competenciaService.remover(id, authentication);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/estagiario/{estagiarioId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CompetenciaDTO>> listarPorEstagiario(@PathVariable Long estagiarioId) {
        List<CompetenciaDTO> competencias = competenciaService.listarPorEstagiario(estagiarioId);
        return ResponseEntity.ok(competencias);
    }
}