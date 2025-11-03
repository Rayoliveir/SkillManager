package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.SupervisorDTO;
import com.senai.skillmanager.dto.SupervisorResponseDTO;
import com.senai.skillmanager.service.SupervisorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/supervisores")
@CrossOrigin(origins = "*")
public class SupervisorController {

    private final SupervisorService supervisorService;

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SupervisorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(supervisorService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GERENTE')")
    public ResponseEntity<SupervisorResponseDTO> buscarPorId(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(supervisorService.buscarPorId(id, authentication));
    }

    @PostMapping
    public ResponseEntity<SupervisorResponseDTO> salvar(@Valid @RequestBody SupervisorDTO dto) {
        SupervisorResponseDTO supervisorSalvo = supervisorService.salvar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(supervisorSalvo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GERENTE')")
    public ResponseEntity<SupervisorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody SupervisorDTO dto, Authentication authentication) {
        SupervisorResponseDTO supervisorAtualizado = supervisorService.atualizar(id, dto, authentication);
        return ResponseEntity.ok(supervisorAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        supervisorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}