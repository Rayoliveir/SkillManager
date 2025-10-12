package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.Genero;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class EstagiarioDTO {
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotNull(message = "A data de nascimento é obrigatória.")
    private LocalDate dataNascimento;

    @NotNull(message = "O gênero é obrigatório.")
    private Genero genero;

    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve ter 10 ou 11 dígitos.")
    private String telefone;

    @Email(message = "Email inválido.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números.")
    private String cpf;

    @NotNull(message = "O endereço é obrigatório.")
    @Valid // <-- Adicionado para validar o objeto de endereço
    private EnderecoDTO endereco;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    // ----- CORREÇÃO PRINCIPAL AQUI -----
    @NotNull(message = "Os dados acadêmicos são obrigatórios.")
    @Valid // <-- Adicionado para validar o objeto de dados acadêmicos
    private DadosAcademicosDTO dadosAcademicos; // <-- Trocado de Long para DadosAcademicosDTO

    // Getters e Setters...
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // ----- GETTER E SETTER ATUALIZADOS -----
    public DadosAcademicosDTO getDadosAcademicos() {
        return dadosAcademicos;
    }

    public void setDadosAcademicos(DadosAcademicosDTO dadosAcademicos) {
        this.dadosAcademicos = dadosAcademicos;
    }
}