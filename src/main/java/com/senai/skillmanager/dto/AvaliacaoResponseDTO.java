package com.senai.skillmanager.dto;

import java.time.LocalDate;

// DTO para ENVIAR uma avaliação como resposta da API
public class AvaliacaoResponseDTO {

    private Long id;
    private String titulo;
    private String feedbackPositivo;
    private String pontosDeMelhoria;
    private Integer notaFrequencia;
    private Integer notaDesempenho;
    private Integer notaOrganizacao;
    private Integer notaParticipacao;
    private Integer notaComportamento;
    private LocalDate dataAvaliacao;
    private SupervisorResponseDTO supervisor; // Usando DTO para evitar expor dados sensíveis
    private EstagiarioResponseDTO estagiario; // Usando DTO

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }
    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }
    public SupervisorResponseDTO getSupervisor() {
        return supervisor;
    }
    public void setSupervisor(SupervisorResponseDTO supervisor) {
        this.supervisor = supervisor;
    }
    public EstagiarioResponseDTO getEstagiario() {
        return estagiario;
    }
    public void setEstagiario(EstagiarioResponseDTO estagiario) {
        this.estagiario = estagiario;
    }
}