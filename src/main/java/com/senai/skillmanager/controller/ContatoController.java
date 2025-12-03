package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.EmailDTO;
import com.senai.skillmanager.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/contato")
@CrossOrigin(origins = "*")
public class ContatoController {

    private final EmailService emailService;

    public ContatoController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> enviarMensagem(@RequestBody EmailDTO emailDTO) {
        try {
            emailService.enviarEmailContato(emailDTO);

            // --- CORREÇÃO: Retorna um Objeto JSON ---
            // Antes: return ResponseEntity.ok("Texto");
            // Agora: return ResponseEntity.ok({ "mensagem": "Texto" });
            return ResponseEntity.ok(Collections.singletonMap("mensagem", "E-mail enviado com sucesso!"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("mensagem", "Erro ao enviar e-mail: " + e.getMessage()));
        }
    }
}