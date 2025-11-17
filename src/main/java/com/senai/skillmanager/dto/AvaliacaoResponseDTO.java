package com.senai.skillmanager.dto;

import java.time.LocalDate;

public class AvaliacaoResponseDTO {

    private Long id;
    private String titulo;
    private String feedback;
    private Integer notaDesempenho;
    private Integer notaHabilidadesTecnicas;
    private Integer notaHabilidadesComportamentais;
    private LocalDate dataAvaliacao;
    private SupervisorResponseDTO supervisor;
    private EstagiarioResponseDTO estagiario;

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