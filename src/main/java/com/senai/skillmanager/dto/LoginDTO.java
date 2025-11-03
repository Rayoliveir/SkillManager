package com.senai.skillmanager.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank(message = "O campo login é obrigatório.")
    private String login;

    @NotBlank(message = "O campo senha é obrigatório.")
    private String senha;

    public LoginDTO() {
    }

    // Getters e Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}