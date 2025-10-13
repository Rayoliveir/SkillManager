package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.AvaliacaoDTO;
import com.senai.skillmanager.dto.AvaliacaoResponseDTO;
import com.senai.skillmanager.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ResponseEntity<AvaliacaoResponseDTO> salvar(@RequestBody @Valid AvaliacaoDTO dto) {
        AvaliacaoResponseDTO avaliacaoSalva = avaliacaoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacaoSalva);
    }

    @GetMapping("/estagiario/{id}")
    @PreAuthorize("isAuthenticated()") // Qualquer usuário autenticado pode ver avaliações
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorEstagiario(@PathVariable Long id) {
        List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.listarPorEstagiario(id);
        return ResponseEntity.ok(avaliacoes);
    }
}