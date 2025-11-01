package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.estagiario.TipoEstagio;
import com.senai.skillmanager.model.empresa.Supervisor;
import java.time.LocalDate;

public class DadosEstagioResponseDTO {
    private Long id;
    private String titulo;
    private TipoEstagio tipo;
    private int cargaHorariaSemanal;
    private boolean isRemunerado;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private String observacoes;
    private Supervisor supervisor;
    private Estagiario estagiario;

    public DadosEstagioResponseDTO() {
    }

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

    public TipoEstagio getTipo() {
        return tipo;
    }

    public void setTipo(TipoEstagio tipo) {
        this.tipo = tipo;
    }

    public int getCargaHorariaSemanal() {
        return cargaHorariaSemanal;
    }

    public void setCargaHorariaSemanal(int cargaHorariaSemanal) {
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }

    // NOME DO MÉTODO CORRIGIDO AQUI
    public boolean getIsRemunerado() {
        return isRemunerado;
    }

    // NOME DO MÉTODO CORRIGIDO AQUI
    public void setIsRemunerado(boolean isRemunerado) {
        this.isRemunerado = isRemunerado;
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Estagiario getEstagiario() {
        return estagiario;
    }

    public void setEstagiario(Estagiario estagiario) {
        this.estagiario = estagiario;
    }
}