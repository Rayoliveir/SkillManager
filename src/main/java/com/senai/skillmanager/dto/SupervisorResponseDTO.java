package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.empresa.Cargo;

public class SupervisorResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private Cargo cargo;
    private EmpresaResponseDTO empresa; // Contém o DTO de Empresa atualizado

    public SupervisorResponseDTO() {
    }

    public SupervisorResponseDTO(Long id, String nome, String email, Cargo cargo, EmpresaResponseDTO empresa) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cargo = cargo;
        this.empresa = empresa;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Cargo getCargo() {
        return cargo;
    }
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    public EmpresaResponseDTO getEmpresa() {
        return empresa;
    }
    public void setEmpresa(EmpresaResponseDTO empresa) {
        this.empresa = empresa;
    }
}