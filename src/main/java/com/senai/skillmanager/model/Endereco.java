package com.senai.skillmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tab_endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O logradouro é obrigatório.")
    private String logradouro;

    @Column(nullable = false)
    @NotBlank(message = "O bairro é obrigatório.")
    private String bairro;

    @Column(nullable = false)
    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;

    @Column(nullable = false)
    @NotBlank(message = "O número da casa é obrigatório.")
    @Pattern(regexp = "\\d+", message = "O número da casa deve conter apenas dígitos numéricos.")
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estados estados;

    @Column(nullable = false)
    @NotBlank(message = "O CEP é obrigatório.")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 dígitos.")
    @Pattern(regexp = "\\d{8}", message = "O CEP deve conter apenas números.")
    private String cep;

    public Endereco() {
    }

    public Endereco(Long id, String logradouro, String bairro, String cidade, String numero, Estados estados, String cep) {
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
