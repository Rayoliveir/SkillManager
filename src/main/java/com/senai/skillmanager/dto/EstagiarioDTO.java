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
    private String telefone;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Email inválido.")
    private String email;

    @NotBlank(message = "O CPF é obrigatório.")
    private String cpf;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    @NotBlank(message = "O código da empresa é obrigatório.")
    private String codigoEmpresa;

    @NotNull(message = "O endereço é obrigatório.")
    @Valid
    private EnderecoDTO endereco;

    @NotNull(message = "Os dados acadêmicos são obrigatórios.")
    @Valid
    private DadosAcademicosDTO dadosAcademicos;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public DadosAcademicosDTO getDadosAcademicos() {
        return dadosAcademicos;
    }

    public void setDadosAcademicos(DadosAcademicosDTO dadosAcademicos) {
        this.dadosAcademicos = dadosAcademicos;
    }
}