package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.DadosEstagioDTO;
import com.senai.skillmanager.dto.DadosEstagioResponseDTO;
import com.senai.skillmanager.service.DadosEstagioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<DadosEstagioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(dadosEstagioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosEstagioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(dadosEstagioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Map<Object, String>> salvar(@Valid @RequestBody DadosEstagioDTO dto) {
        dadosEstagioService.salvar(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("mensagem", "Dados de estágio cadastrados com sucesso."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<Object, String>> atualizar(@PathVariable Long id, @Valid @RequestBody DadosEstagioDTO dto) {
        dadosEstagioService.atualizar(id, dto);
        return ResponseEntity.ok(Map.of("mensagem", "Dados de estágio atualizados com sucesso."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<Object, String>> excluir(@PathVariable Long id) {
        dadosEstagioService.excluir(id);
        return ResponseEntity.ok(Map.of("mensagem", "Dados de estágio excluídos com sucesso."));
    }
}