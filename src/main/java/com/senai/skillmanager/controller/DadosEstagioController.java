package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.DadosEstagioDTO;
import com.senai.skillmanager.dto.DadosEstagioResponseDTO;
import com.senai.skillmanager.service.DadosEstagioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dados-estagio")
@CrossOrigin(origins = "*")
public class DadosEstagioController {

    private final DadosEstagioService dadosEstagioService;

    public DadosEstagioController(DadosEstagioService dadosEstagioService) {
        this.dadosEstagioService = dadosEstagioService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'SUPERVISOR')")
    public ResponseEntity<DadosEstagioResponseDTO> salvar(@Valid @RequestBody DadosEstagioDTO dto, Authentication authentication) {
        DadosEstagioResponseDTO response = dadosEstagioService.salvar(dto, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DadosEstagioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(dadosEstagioService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DadosEstagioResponseDTO> buscarPorId(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(dadosEstagioService.buscarPorId(id, authentication));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'SUPERVISOR')")
    public ResponseEntity<DadosEstagioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody DadosEstagioDTO dto, Authentication authentication) {
        return ResponseEntity.ok(dadosEstagioService.atualizar(id, dto, authentication));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE', 'SUPERVISOR', 'ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        dadosEstagioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}