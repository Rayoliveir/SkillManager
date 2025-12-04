package com.senai.skillmanager.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError; // Importante para pegar o nome do campo
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // --- 1. ERROS DE VALIDAÇÃO (O que está causando o 400 no cadastro) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException erro) {

        // LOG NO CONSOLE DO RENDER (Para você ver o que está errado)
        System.out.println("\n❌ [ERRO DE VALIDAÇÃO 400 DETECTADO] --------------------------------");
        erro.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            System.out.println("   --> CAMPO: [" + fieldName + "] | ERRO: " + errorMessage);
        });
        System.out.println("--------------------------------------------------------------------\n");

        // RESPOSTA PARA O FRONT-END
        String mensagemErro = erro.getFieldErrors().isEmpty()
                ? "Erro de validação nos dados enviados."
                : erro.getFieldErrors().get(0).getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("mensagem", "Erro nos dados: " + mensagemErro));
    }

    // --- 2. ERRO GENÉRICO (Para pegar null pointers ou erros de banco) ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handlerGenericException(Exception erro) {
        System.out.println("🔥 [ERRO INTERNO NÃO TRATADO]: " + erro.getMessage());
        erro.printStackTrace(); // Mostra a stack trace no log do Render

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("mensagem", "Ocorreu um erro interno no servidor: " + erro.getMessage()));
    }

    // --- 3. ARGUMENTO INVÁLIDO ---
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handlerIllegalArgument(IllegalArgumentException erro) {
        System.out.println("⚠️ [ERRO 400 - Illegal Argument]: " + erro.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("mensagem", erro.getMessage()));
    }

    // --- 4. ENTIDADE NÃO ENCONTRADA ---
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerEntityNotFoundException(EntityNotFoundException erro) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensagem", erro.getMessage()));
    }

    // --- 5. EMAIL DUPLICADO ---
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<Map<String, Object>> handlerEmailJaCadastradoException(EmailJaCadastradoException erro) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("mensagem", erro.getMessage()));
    }

    // --- 6. SEGURANÇA (CheckOwnership manual) ---
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, Object>> handlerSecurityException(SecurityException erro) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("mensagem", erro.getMessage()));
    }

    // --- 7. SEGURANÇA (Spring Security Annotations) ---
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, Object>> handlerAuthorizationDeniedException(AuthorizationDeniedException erro) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("mensagem", "Acesso negado. Você não tem permissão para acessar este recurso."));
    }

    // --- 8. ROTA NÃO ENCONTRADA ---
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerNoResourceFoundException(NoResourceFoundException erro) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensagem", "Recurso não encontrado: " + erro.getMessage()));
    }

    // --- 9. MÉTODO HTTP NÃO SUPORTADO ---
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException erro) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(Map.of("mensagem", "Método não permitido: " + erro.getMessage()));
    }
}