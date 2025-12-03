package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.EmailDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetenteOficial;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarEmailContato(EmailDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Configurações do e-mail
        // O "From" técnico é o seu email autenticado, mas o "Reply-To" será o do usuário
        message.setFrom(remetenteOficial);
        message.setTo(dto.getTo());
        message.setSubject(dto.getSubject());

        // Corpo do e-mail
        String corpoEmail = "Nova mensagem de contato recebida pelo site:\n\n" +
                "Nome: " + dto.getFromName() + "\n" +
                "Email do Usuário: " + dto.getFrom() + "\n\n" +
                "Mensagem:\n" + dto.getMessage();

        message.setText(corpoEmail);

        // Se você clicar em "Responder" no Gmail, vai para o usuário que mandou a msg
        message.setReplyTo(dto.getFrom());

        mailSender.send(message);
    }
}