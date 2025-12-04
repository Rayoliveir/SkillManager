package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.empresa.TipoEmpresa; // Importa o novo Enum
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class EmpresaDTO {
    @NotBlank(message = "O nome da empresa é obrigatório.")
    private String nome;

    @NotBlank(message = "A razão social é obrigatória.")
    private String razaoSocial;

    @NotBlank(message = "O CNPJ é obrigatório.")
    @Pattern(regexp = "^[0-9]{14}$", message = "O CNPJ deve ter 14 dígitos numéricos.")
    private String cnpj;

    // --- NOVOS CAMPOS ADICIONADOS ---
    @NotNull(message = "O tipo da empresa é obrigatório.")
    private TipoEmpresa tipoEmpresa;

    private String inscricaoEstadual; // Opcional no DTO, validado no Service
    private String inscricaoMunicipal; // Opcional no DTO, validado no Service

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
    public String getRazaoSocial() {
        return razaoSocial;
    }
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }
    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }
    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }
    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }
    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }
    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }
    public EnderecoDTO getEndereco() {
        return endereco;
    }
    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }
}