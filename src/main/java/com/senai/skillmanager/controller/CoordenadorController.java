package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.CoordenadorDTO;
import com.senai.skillmanager.dto.CoordenadorResponseDTO;
import com.senai.skillmanager.service.CoordenadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordenadores")
@CrossOrigin(origins = "*")
public class CoordenadorController {

    private final CoordenadorService coordenadorService;

    public CoordenadorController(CoordenadorService coordenadorService) {
        this.coordenadorService = coordenadorService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // Apenas Admin pode listar todos coordenadores
    public ResponseEntity<List<CoordenadorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(coordenadorService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULDADE')") // Admin ou o próprio coordenador (ROLE_FACULDADE)
    public ResponseEntity<CoordenadorResponseDTO> buscarPorId(@PathVariable Long id) {
        // TODO: Adicionar lógica para o coordenador ver apenas o próprio perfil
        return ResponseEntity.ok(coordenadorService.buscarPorId(id));
    }

    @PostMapping
    // Endpoint público para cadastro (Fluxo A e B)
    public ResponseEntity<CoordenadorResponseDTO> salvar(@Valid @RequestBody CoordenadorDTO dto) {
        CoordenadorResponseDTO coordenadorSalvo = coordenadorService.salvar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(coordenadorSalvo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULDADE')") // Admin ou o próprio coordenador
    public ResponseEntity<CoordenadorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CoordenadorDTO dto) {
        // TODO: Adicionar lógica para o coordenador atualizar apenas o próprio perfil
        CoordenadorResponseDTO coordenadorAtualizado = coordenadorService.atualizar(id, dto);
        return ResponseEntity.ok(coordenadorAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        coordenadorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}