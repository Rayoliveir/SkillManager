package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.CoordenadorDTO;
import com.senai.skillmanager.dto.CoordenadorResponseDTO;
import com.senai.skillmanager.service.CoordenadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<CoordenadorResponseDTO> salvar(@Valid @RequestBody CoordenadorDTO dto) {
        CoordenadorResponseDTO novoCoordenador = coordenadorService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCoordenador);
    }

    @GetMapping
    public ResponseEntity<List<CoordenadorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(coordenadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordenadorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(coordenadorService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoordenadorResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CoordenadorDTO dto) {
        return ResponseEntity.ok(coordenadorService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        coordenadorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}