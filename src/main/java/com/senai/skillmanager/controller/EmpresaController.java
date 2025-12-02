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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmpresaResponseDTO> salvar(@Valid @RequestBody EmpresaDTO dto) {
        EmpresaResponseDTO empresaSalva = empresaService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaSalva);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmpresaResponseDTO>> listarTodos() {
        List<EmpresaResponseDTO> empresas = empresaService.listarTodos();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable Long id) {
        EmpresaResponseDTO empresa = empresaService.buscarPorId(id);
        return ResponseEntity.ok(empresa);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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