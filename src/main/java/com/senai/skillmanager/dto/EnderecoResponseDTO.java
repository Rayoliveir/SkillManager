package com.senai.skillmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senai.skillmanager.model.Estados;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnderecoResponseDTO {
    private Long id;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String numero;
    private Estados estados;
    private String cep;

    public EnderecoResponseDTO() {
    }

    public EnderecoResponseDTO(Long id, String logradouro, String bairro, String cidade, String numero, Estados estados, String cep) {
        this.id = id;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.estados = estados;
        this.cep = cep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Estados getEstados() {
        return estados;
    }

    public void setEstados(Estados estados) {
        this.estados = estados;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}