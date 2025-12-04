package com.senai.skillmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("🔧 Configuração de CORS Ativada!"); // Log para debug no Render

        registry.addMapping("/**") // Aplica a todas as rotas da API
                .allowedOriginPatterns("*") // LIBERA TUDO: Aceita Front-end do Render, Localhost, etc.
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT")
                .allowedHeaders("*") // Aceita qualquer cabeçalho (Token, Content-Type, etc)
                .allowCredentials(true); // Permite envio de credenciais/cookies
    }
}