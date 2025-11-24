package com.senai.skillmanager.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException; // <-- ADICIONADO
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handlerIllegalArgument(IllegalArgumentException erro){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("mensagem", erro.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerEntityNotFoundException(EntityNotFoundException erro) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensagem", erro.getMessage()));
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<Map<String, Object>> handlerEmailJaCadastradoException(EmailJaCadastradoException erro){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("mensagem", erro.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException erro){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("mensagem", erro.getFieldErrors().get(0).getDefaultMessage()));
    }

    // O handler para o nosso checkOwnership manual
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, Object>> handlerSecurityException(SecurityException erro) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("mensagem", erro.getMessage()));
    }

    // --- NOVO HANDLER ADICIONADO ---
    // O handler para o @PreAuthorize automático do Spring
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, Object>> handlerAuthorizationDeniedException(AuthorizationDeniedException erro) {
        // Usamos uma mensagem fixa aqui, pois o "erro.getMessage()" padrão é "Access Denied"
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("mensagem", "Acesso negado. Você não tem permissão para acessar este recurso."));
    }
    // -------------------------------

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerNoResourceFoundException(NoResourceFoundException erro){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensagem", erro.getMessage()));
    }


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException erro){
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(Map.of("mensagem", erro.getMessage()));
    }
}