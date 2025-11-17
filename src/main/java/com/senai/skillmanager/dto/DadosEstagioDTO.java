package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.estagiario.TipoEstagio;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class DadosEstagioDTO {

    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @NotNull(message = "A data de início é obrigatória.")
    @FutureOrPresent(message = "A data de início deve ser hoje ou uma data futura.")
    private LocalDate dataInicio;

    private LocalDate dataTermino;

    @NotNull(message = "A carga horária é obrigatória.")
    private Integer cargaHoraria;

    @NotNull(message = "O tipo de estágio é obrigatório.")
    private TipoEstagio tipoEstagio;

    @NotNull(message = "O ID do estagiário é obrigatório.")
    private Long estagiarioId;

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

    public Long getEstagiarioId() {
        return estagiarioId;
    }

    public void setEstagiarioId(Long estagiarioId) {
        this.estagiarioId = estagiarioId;
    }
}