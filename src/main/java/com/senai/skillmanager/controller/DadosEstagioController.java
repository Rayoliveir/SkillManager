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
import java.util.Map;

@RestController
@RequestMapping("/dados-estagio")
@CrossOrigin(origins = "*")
public class DadosEstagioController {
    private final DadosEstagioService dadosEstagioService;

    public DadosEstagioController(DadosEstagioService dadosEstagioService) {
        this.dadosEstagioService = dadosEstagioService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DadosEstagioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(dadosEstagioService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DadosEstagioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(dadosEstagioService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GERENTE')")
    public ResponseEntity<Map<Object, String>> salvar(@Valid @RequestBody DadosEstagioDTO dto, Authentication authentication) {
        dadosEstagioService.salvar(dto, authentication);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Dados de estágio cadastrados com sucesso."));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GERENTE')")
    public ResponseEntity<Map<Object, String>> atualizar(@PathVariable Long id, @Valid @RequestBody DadosEstagioDTO dto, Authentication authentication) {
        dadosEstagioService.atualizar(id, dto, authentication);
        return ResponseEntity.ok(Map.of("mensagem", "Dados de estágio atualizados com sucesso."));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'GERENTE')")
    public ResponseEntity<Map<Object, String>> excluir(@PathVariable Long id) {
        dadosEstagioService.excluir(id);
        return ResponseEntity.ok(Map.of("mensagem", "Dados de estágio excluídos com sucesso."));
    }
}