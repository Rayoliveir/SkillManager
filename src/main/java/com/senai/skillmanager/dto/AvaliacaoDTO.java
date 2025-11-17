package com.senai.skillmanager.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AvaliacaoDTO {

    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    private String titulo;

    @Size(max = 1000, message = "O feedback deve ter no máximo 1000 caracteres.")
    private String feedback;

    @NotNull(message = "A nota de desempenho é obrigatória.")
    @Min(value = 1, message = "A nota deve ser no mínimo 1.")
    @Max(value = 5, message = "A nota deve ser no máximo 5.")
    private Integer notaDesempenho;

    @NotNull(message = "A nota de habilidades técnicas é obrigatória.")
    @Min(value = 1, message = "A nota deve ser no mínimo 1.")
    @Max(value = 5, message = "A nota deve ser no máximo 5.")
    private Integer notaHabilidadesTecnicas;

    @NotNull(message = "A nota de habilidades comportamentais é obrigatória.")
    @Min(value = 1, message = "A nota deve ser no mínimo 1.")
    @Max(value = 5, message = "A nota deve ser no máximo 5.")
    private Integer notaHabilidadesComportamentais;

    @NotNull(message = "O ID do estagiário é obrigatório.")
    private Long estagiarioId;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getNotaDesempenho() {
        return notaDesempenho;
    }

    public void setNotaDesempenho(Integer notaDesempenho) {
        this.notaDesempenho = notaDesempenho;
    }

    public Integer getNotaHabilidadesTecnicas() {
        return notaHabilidadesTecnicas;
    }

    public void setNotaHabilidadesTecnicas(Integer notaHabilidadesTecnicas) {
        this.notaHabilidadesTecnicas = notaHabilidadesTecnicas;
    }

    public Integer getNotaHabilidadesComportamentais() {
        return notaHabilidadesComportamentais;
    }

    public void setNotaHabilidadesComportamentais(Integer notaHabilidadesComportamentais) {
        this.notaHabilidadesComportamentais = notaHabilidadesComportamentais;
    }

    public Long getEstagiarioId() {
        return estagiarioId;
    }

    public void setEstagiarioId(Long estagiarioId) {
        this.estagiarioId = estagiarioId;
    }
}