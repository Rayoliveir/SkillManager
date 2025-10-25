package com.senai.skillmanager.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class FaculdadeDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Pattern(regexp = "^[0-9]{14}$", message = "O CNPJ deve ter 14 dígitos numéricos.")
    @NotBlank(message = "O CNPJ é obrigatório.")
    private String cnpj;

    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve ter 10 ou 11 dígitos.")
    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    private String site;

    @NotNull(message = "O endereço é obrigatório.")
    @Valid
    private EnderecoDTO endereco;

    // Getters e Setters
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public EnderecoDTO getEndereco() {
        return endereco;
    }
    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}