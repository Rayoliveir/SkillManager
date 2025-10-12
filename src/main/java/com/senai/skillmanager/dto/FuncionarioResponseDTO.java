package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.Empresa;
import com.senai.skillmanager.model.funcionario.Cargo;

public class FuncionarioResponseDTO {
    private Long id; // CAMPO ID ADICIONADO AQUI
    private String nome;
    private String email;
    private Cargo cargo;
    private Empresa empresa;

    public FuncionarioResponseDTO() {
    }

    public FuncionarioResponseDTO(Long id, String nome, String email, Cargo cargo, Empresa empresa) {
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

    public void setId(Long id) { // MÉTODO SETID ADICIONADO AQUI
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}