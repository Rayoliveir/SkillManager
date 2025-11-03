package com.senai.skillmanager.dto;

import jakarta.validation.constraints.*;

// DTO para CRIAR uma nova avaliação
public class AvaliacaoDTO {

    @NotBlank
    private String titulo;
    private String feedbackPositivo;
    private String pontosDeMelhoria;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer notaFrequencia;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer notaDesempenho;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer notaOrganizacao;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer notaParticipacao;
    @NotNull
    @Min(1)
    @Max(5)
    private Integer notaComportamento;

    @NotNull
    private Long supervisorId;
    @NotNull
    private Long estagiarioId;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFeedbackPositivo() {
        return feedbackPositivo;
    }

    public void setFeedbackPositivo(String feedbackPositivo) {
        this.feedbackPositivo = feedbackPositivo;
    }

    public String getPontosDeMelhoria() {
        return pontosDeMelhoria;
    }

    public void setPontosDeMelhoria(String pontosDeMelhoria) {
        this.pontosDeMelhoria = pontosDeMelhoria;
    }

    public Integer getNotaFrequencia() {
        return notaFrequencia;
    }

    public void setNotaFrequencia(Integer notaFrequencia) {
        this.notaFrequencia = notaFrequencia;
    }

    public Integer getNotaDesempenho() {
        return notaDesempenho;
    }

    public void setNotaDesempenho(Integer notaDesempenho) {
        this.notaDesempenho = notaDesempenho;
    }

    public Integer getNotaOrganizacao() {
        return notaOrganizacao;
    }

    public void setNotaOrganizacao(Integer notaOrganizacao) {
        this.notaOrganizacao = notaOrganizacao;
    }

    public Integer getNotaParticipacao() {
        return notaParticipacao;
    }

    public void setNotaParticipacao(Integer notaParticipacao) {
        this.notaParticipacao = notaParticipacao;
    }

    public Integer getNotaComportamento() {
        return notaComportamento;
    }

    public void setNotaComportamento(Integer notaComportamento) {
        this.notaComportamento = notaComportamento;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Long getEstagiarioId() {
        return estagiarioId;
    }

    public void setEstagiarioId(Long estagiarioId) {
        this.estagiarioId = estagiarioId;
    }
}