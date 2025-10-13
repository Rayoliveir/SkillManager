package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.Genero;
import java.time.LocalDate;
import java.util.List;

public class EstagiarioResponseDTO {
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private Genero genero;
    private String telefone;
    private String email;
    private String cpf;
    private EnderecoResponseDTO endereco; // <-- Ajustado para usar o DTO de resposta
    private DadosAcademicosResponseDTO dadosAcademicos; // <-- Ajustado para usar o DTO de resposta
    private List<DadosEstagioResponseDTO> dadosEstagio;

    // Getters e Setters...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public EnderecoResponseDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoResponseDTO endereco) {
        this.endereco = endereco;
    }

    public DadosAcademicosResponseDTO getDadosAcademicos() {
        return dadosAcademicos;
    }

    public void setDadosAcademicos(DadosAcademicosResponseDTO dadosAcademicos) {
        this.dadosAcademicos = dadosAcademicos;
    }

    public List<DadosEstagioResponseDTO> getDadosEstagio() {
        return dadosEstagio;
    }

    public void setDadosEstagio(List<DadosEstagioResponseDTO> dadosEstagio) {
        this.dadosEstagio = dadosEstagio;
    }
}