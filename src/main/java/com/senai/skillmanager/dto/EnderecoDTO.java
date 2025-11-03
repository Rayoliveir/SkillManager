package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.Estados;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EnderecoDTO {
    @NotBlank(message = "O logradouro é obrigatório.")
    private String logradouro;

    @NotBlank(message = "O bairro é obrigatório.")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;

    @NotBlank(message = "O número da casa é obrigatório.")
    @Pattern(regexp = "\\d+", message = "O número da casa deve conter apenas dígitos numéricos.")
    private String numero;

    @NotNull(message = "O estado é obrigatório.")
    private Estados estados;

    @NotBlank(message = "O CEP é obrigatório.")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 dígitos.")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter apenas números.")
    private String cep;

    public EnderecoDTO() {
    }

    public EnderecoDTO(String logradouro, String bairro, String cidade, String numero, Estados estados, String cep) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.numero = numero;
        this.estados = estados;
        this.cep = cep;
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

    @Override
    public String toString() {
        return "EnderecoDTO{" +
                "logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade='" + cidade + '\'' +
                ", numero='" + numero + '\'' +
                ", estados=" + estados +
                ", cep='" + cep + '\'' +
                '}';
    }
}