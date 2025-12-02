package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.EstagiarioDTO;
import com.senai.skillmanager.dto.EstagiarioResponseDTO;
import com.senai.skillmanager.service.EstagiarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PostMapping
    public ResponseEntity<EstagiarioResponseDTO> salvar(@Valid @RequestBody EstagiarioDTO dto) {
        EstagiarioResponseDTO estagiarioSalvo = estagiarioService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(estagiarioSalvo);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EstagiarioResponseDTO>> listarTodos() {
        List<EstagiarioResponseDTO> estagiarios = estagiarioService.listarTodos();
        return ResponseEntity.ok(estagiarios);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EstagiarioResponseDTO> buscarPorId(@PathVariable Long id, Authentication authentication) {
        // SEGURANÇA: Passamos o authentication para o Service validar a regra de negócio
        EstagiarioResponseDTO estagiario = estagiarioService.buscarPorId(id, authentication);
        return ResponseEntity.ok(estagiario);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ESTAGIARIO')")
    public ResponseEntity<EstagiarioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EstagiarioDTO dto, Authentication authentication) {
        // SEGURANÇA: Passamos o authentication
        EstagiarioResponseDTO estagiarioAtualizado = estagiarioService.atualizar(id, dto, authentication);
        return ResponseEntity.ok(estagiarioAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GERENTE')")
    public ResponseEntity<Void> excluir(@PathVariable Long id, Authentication authentication) {
        // SEGURANÇA: Passamos o authentication
        estagiarioService.excluir(id, authentication);
        return ResponseEntity.noContent().build();
    }
}