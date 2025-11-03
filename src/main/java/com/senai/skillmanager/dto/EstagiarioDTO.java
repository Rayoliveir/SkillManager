package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class EstagiarioDTO {

    @NotBlank
    private String nome;
    @NotNull
    private LocalDate dataNascimento;
    @NotNull
    private Genero genero;
    @NotBlank
    private String telefone;
    @NotBlank
    private String email;
    @NotBlank
    private String cpf;
    @NotBlank
    private String senha;
    @NotNull
    private EnderecoDTO endereco;
    @NotNull
    private DadosAcademicosDTO dadosAcademicos;
    @NotBlank(message = "O código da empresa é obrigatório.")
    private String codigoEmpresa;

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
    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }
    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
}