package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.DadosAcademicosDTO;
import com.senai.skillmanager.dto.DadosAcademicosResponseDTO;
import com.senai.skillmanager.service.DadosAcademicosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dados-academicos")
@CrossOrigin(origins = "*")
public class DadosAcademicosController {
    private final DadosAcademicosService dadosAcademicosService;

    public DadosAcademicosController(DadosAcademicosService dadosAcademicosService) {
        this.dadosAcademicosService = dadosAcademicosService;
    }

    @GetMapping
    public ResponseEntity<List<DadosAcademicosResponseDTO>> listarTodos() {
        return ResponseEntity.ok(dadosAcademicosService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosAcademicosResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(dadosAcademicosService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Map<Object, String>> salvar(@Valid @RequestBody DadosAcademicosDTO dto) {
        dadosAcademicosService.salvar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Dados acadêmicos cadastrados com sucesso."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<Object, String>> atualizar(@PathVariable Long id, @Valid @RequestBody DadosAcademicosDTO dto) {
        dadosAcademicosService.atualizar(id, dto);
        return ResponseEntity.ok(Map.of("mensagem", "Dados acadêmicos atualizados com sucesso."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<Object, String>> excluir(@PathVariable Long id) {
        dadosAcademicosService.excluir(id);
        return ResponseEntity.ok(Map.of("mensagem", "Dados acadêmicos excluídos com sucesso."));
    }
}