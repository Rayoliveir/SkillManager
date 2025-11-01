package com.senai.skillmanager.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CoordenadorDTO {
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Digite um email válido.")
    private String email;

    @NotBlank(message = "Senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    // --- MUDANÇA CRUCIAL (BASEADA NA SUA IDEIA) ---

    @NotBlank(message = "O CNPJ da faculdade é obrigatório.")
    @Pattern(regexp = "^[0-9]{14}$", message = "O CNPJ deve ter 14 dígitos numéricos.")
    private String faculdadeCnpj;

    // Campos opcionais para o Fluxo A (criar nova faculdade)
    private String faculdadeNome;
    private String faculdadeTelefone;
    private String faculdadeSite;
    @Valid // Valida o endereço se ele for fornecido
    private EnderecoDTO faculdadeEndereco;

    // Getters e Setters
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getFaculdadeCnpj() {
        return faculdadeCnpj;
    }
    public void setFaculdadeCnpj(String faculdadeCnpj) {
        this.faculdadeCnpj = faculdadeCnpj;
    }
    public String getFaculdadeNome() {
        return faculdadeNome;
    }
    public void setFaculdadeNome(String faculdadeNome) {
        this.faculdadeNome = faculdadeNome;
    }
    public String getFaculdadeTelefone() {
        return faculdadeTelefone;
    }
    public void setFaculdadeTelefone(String faculdadeTelefone) {
        this.faculdadeTelefone = faculdadeTelefone;
    }
    public String getFaculdadeSite() {
        return faculdadeSite;
    }
    public void setFaculdadeSite(String faculdadeSite) {
        this.faculdadeSite = faculdadeSite;
    }
    public EnderecoDTO getFaculdadeEndereco() {
        return faculdadeEndereco;
    }
    public void setFaculdadeEndereco(EnderecoDTO faculdadeEndereco) {
        this.faculdadeEndereco = faculdadeEndereco;
    }
}