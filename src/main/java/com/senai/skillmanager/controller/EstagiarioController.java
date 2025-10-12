package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.EstagiarioDTO;
import com.senai.skillmanager.dto.EstagiarioResponseDTO;
import com.senai.skillmanager.service.EstagiarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estagiarios")
@CrossOrigin(origins = "*")
public class EstagiarioController {
    private final EstagiarioService estagiarioService;

    public EstagiarioController(EstagiarioService estagiarioService) {
        this.estagiarioService = estagiarioService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ESTAGIARIO', 'ADMIN')")
    public ResponseEntity<List<EstagiarioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(estagiarioService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ESTAGIARIO', 'ADMIN')")
    public ResponseEntity<EstagiarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estagiarioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EstagiarioResponseDTO> salvar(@Valid @RequestBody EstagiarioDTO dto) {
        EstagiarioResponseDTO estagiarioSalvo = estagiarioService.salvar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estagiarioSalvo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ESTAGIARIO', 'ADMIN')")
    public ResponseEntity<EstagiarioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EstagiarioDTO dto) {
        EstagiarioResponseDTO estagiarioAtualizado = estagiarioService.atualizar(id, dto);
        return ResponseEntity.ok(estagiarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        estagiarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}