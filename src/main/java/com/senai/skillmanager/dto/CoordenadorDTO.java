package com.senai.skillmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CoordenadorDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    // --- CORREÇÃO: Senha opcional na edição ---
    @Pattern(regexp = "^$|.{6,}", message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;
    // ------------------------------------------

    // Dados da Faculdade
    @NotBlank(message = "O CNPJ da faculdade é obrigatório")
    private String faculdadeCnpj;

    private String faculdadeNome;
    private String faculdadeTelefone;
    private String faculdadeSite;
    private EnderecoDTO faculdadeEndereco;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getFaculdadeCnpj() { return faculdadeCnpj; }
    public void setFaculdadeCnpj(String faculdadeCnpj) { this.faculdadeCnpj = faculdadeCnpj; }

    public String getFaculdadeNome() { return faculdadeNome; }
    public void setFaculdadeNome(String faculdadeNome) { this.faculdadeNome = faculdadeNome; }

    public String getFaculdadeTelefone() { return faculdadeTelefone; }
    public void setFaculdadeTelefone(String faculdadeTelefone) { this.faculdadeTelefone = faculdadeTelefone; }

    public String getFaculdadeSite() { return faculdadeSite; }
    public void setFaculdadeSite(String faculdadeSite) { this.faculdadeSite = faculdadeSite; }

    public EnderecoDTO getFaculdadeEndereco() { return faculdadeEndereco; }
    public void setFaculdadeEndereco(EnderecoDTO faculdadeEndereco) { this.faculdadeEndereco = faculdadeEndereco; }
}