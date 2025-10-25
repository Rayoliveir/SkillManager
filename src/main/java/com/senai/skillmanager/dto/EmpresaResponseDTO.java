package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.empresa.TipoEmpresa; // Importa o novo Enum

public class EmpresaResponseDTO {
    private Long id;
    private String nome;
    private String razaoSocial;
    private String cnpj;

    // --- NOVOS CAMPOS ADICIONADOS ---
    private TipoEmpresa tipoEmpresa;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private EnderecoDTO endereco;

    public EmpresaResponseDTO() {
    }

    // Getters e Setters
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