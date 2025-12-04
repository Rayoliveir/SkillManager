package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.competencia.NivelCompetencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CompetenciaDTO {

    private Long id;

    @NotBlank(message = "O nome da competência é obrigatório.")
    private String nome;

    @NotNull(message = "O nível da competência é obrigatório.")
    private NivelCompetencia nivel;

    // O ID do estagiário é necessário para saber a quem atribuir
    private Long estagiarioId;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public NivelCompetencia getNivel() { return nivel; }
    public void setNivel(NivelCompetencia nivel) { this.nivel = nivel; }

    public Long getEstagiarioId() { return estagiarioId; }
    public void setEstagiarioId(Long estagiarioId) { this.estagiarioId = estagiarioId; }
}