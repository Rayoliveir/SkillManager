package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.SupervisorDTO;
import com.senai.skillmanager.dto.SupervisorResponseDTO;
import com.senai.skillmanager.service.SupervisorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<SupervisorResponseDTO> salvar(@Valid @RequestBody SupervisorDTO dto) {
        SupervisorResponseDTO novoSupervisor = supervisorService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoSupervisor);
    }

    @GetMapping
    public ResponseEntity<List<SupervisorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(supervisorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupervisorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(supervisorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupervisorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody SupervisorDTO dto) {
        return ResponseEntity.ok(supervisorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        supervisorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}