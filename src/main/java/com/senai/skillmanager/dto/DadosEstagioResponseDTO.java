package com.senai.skillmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.senai.skillmanager.model.estagiario.TipoEstagio;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DadosEstagioResponseDTO {

    private Long id;
    private String titulo;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private Integer cargaHoraria;
    private TipoEstagio tipoEstagio;
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public TipoEstagio getTipoEstagio() {
        return tipoEstagio;
    }

    public void setTipoEstagio(TipoEstagio tipoEstagio) {
        this.tipoEstagio = tipoEstagio;
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