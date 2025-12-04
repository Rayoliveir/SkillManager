package com.senai.skillmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoordenadorResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private FaculdadeResponseDTO faculdade;

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

    public FaculdadeResponseDTO getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(FaculdadeResponseDTO faculdade) {
        this.faculdade = faculdade;
    }
}