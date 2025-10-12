package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.estagiario.TipoEstagio;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class DadosEstagioDTO {
    @NotBlank(message = "O título do estágio é obrigatório.")
    private String titulo;

    @NotNull(message = "O tipo de estágio é obrigatório.")
    private TipoEstagio tipo;

    @NotNull(message = "A carga horária é obrigatória.")
    @Min(value = 1, message = "A carga horária deve ser maior que 0.")
    private int cargaHorariaSemanal;

    @NotNull(message = "O status de remuneração é obrigatório.")
    private boolean isRemunerado;

    @NotNull(message = "A data de início é obrigatória.")
    private LocalDate dataInicio;

    @NotNull(message = "A data de término é obrigatória.")
    private LocalDate dataTermino;

    private String observacoes;

    @NotNull(message = "O ID do supervisor é obrigatório.")
    private Long supervisorId;

    @NotNull(message = "O ID do estagiário é obrigatório.")
    private Long estagiarioId;

    public DadosEstagioDTO() {
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