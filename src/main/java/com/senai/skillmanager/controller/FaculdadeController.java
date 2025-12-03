package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.FaculdadeDTO;
import com.senai.skillmanager.dto.FaculdadeResponseDTO;
import com.senai.skillmanager.service.FaculdadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculdades")
@CrossOrigin(origins = "*")
public class FaculdadeController {
    private final FaculdadeService faculdadeService;

    public FaculdadeController(FaculdadeService faculdadeService) {
        this.faculdadeService = faculdadeService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()") // Qualquer usuário autenticado pode listar faculdades
    public ResponseEntity<List<FaculdadeResponseDTO>> listarTodos() {
        return ResponseEntity.ok(faculdadeService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // Qualquer usuário autenticado pode ver uma faculdade
    public ResponseEntity<FaculdadeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(faculdadeService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Apenas Admin pode criar uma faculdade manualmente
    public ResponseEntity<FaculdadeResponseDTO> salvar(@Valid @RequestBody FaculdadeDTO dto) {
        FaculdadeResponseDTO faculdadeSalva = faculdadeService.salvar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(faculdadeSalva);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Apenas Admin pode atualizar a instituição
    public ResponseEntity<FaculdadeResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FaculdadeDTO dto) {
        FaculdadeResponseDTO faculdadeAtualizada = faculdadeService.atualizar(id, dto);
        return ResponseEntity.ok(faculdadeAtualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        faculdadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}