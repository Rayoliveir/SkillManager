package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.DadosAcademicosDTO;
import com.senai.skillmanager.dto.DadosAcademicosResponseDTO;
import com.senai.skillmanager.service.DadosAcademicosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dados-academicos")
@CrossOrigin(origins = "*")
public class DadosAcademicosController {

    private final DadosAcademicosService dadosAcademicosService;

    public DadosAcademicosController(DadosAcademicosService dadosAcademicosService) {
        this.dadosAcademicosService = dadosAcademicosService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DadosAcademicosResponseDTO> salvar(@Valid @RequestBody DadosAcademicosDTO dto) {
        DadosAcademicosResponseDTO response = dadosAcademicosService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DadosAcademicosResponseDTO>> listarTodos() {
        List<DadosAcademicosResponseDTO> response = dadosAcademicosService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DadosAcademicosResponseDTO> buscarPorId(@PathVariable Long id, Authentication authentication) {
        DadosAcademicosResponseDTO response = dadosAcademicosService.buscarPorId(id, authentication);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DadosAcademicosResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody DadosAcademicosDTO dto, Authentication authentication) {
        DadosAcademicosResponseDTO response = dadosAcademicosService.atualizar(id, dto, authentication);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        dadosAcademicosService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}