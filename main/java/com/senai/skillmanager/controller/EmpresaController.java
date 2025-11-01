package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.EmpresaDTO;
import com.senai.skillmanager.dto.EmpresaResponseDTO;
import com.senai.skillmanager.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()") // Qualquer usuário autenticado pode listar empresas
    public ResponseEntity<List<EmpresaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(empresaService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // Qualquer usuário autenticado pode ver uma empresa
    public ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Apenas Admin pode criar uma empresa manualmente
    public ResponseEntity<EmpresaResponseDTO> salvar(@Valid @RequestBody EmpresaDTO dto) {
        EmpresaResponseDTO empresaSalva = empresaService.salvar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(empresaSalva);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Apenas Admin pode atualizar os dados da instituição
    public ResponseEntity<EmpresaResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EmpresaDTO dto) {
        EmpresaResponseDTO empresaAtualizada = empresaService.atualizar(id, dto);
        return ResponseEntity.ok(empresaAtualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        empresaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}