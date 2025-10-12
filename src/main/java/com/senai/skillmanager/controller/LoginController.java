package com.senai.skillmanager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/login")
public class LoginController {

    // Este controller agora é extremamente simples.
    // Se a requisição chegar até aqui, significa que o httpBasic JÁ autenticou o usuário.
    @PostMapping
    public ResponseEntity<Map<String, Object>> login(Authentication authentication) {
        // A 'authentication' é injetada automaticamente pelo Spring Security após o sucesso.
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login realizado com sucesso.");
        response.put("username", authentication.getName());
        response.put("roles", authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }
}