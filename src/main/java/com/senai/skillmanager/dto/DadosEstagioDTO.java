package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.estagiario.TipoEstagio;
import com.senai.skillmanager.model.estagiario.TipoRemuneracao; // Importe o novo Enum
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class DadosEstagioDTO {

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDate dataInicio;

    private LocalDate dataTermino;

    @NotNull(message = "A carga horária é obrigatória")
    private Integer cargaHoraria;

    private String observacoes;

    @NotNull(message = "O tipo de estágio é obrigatório")
    private TipoEstagio tipoEstagio;

    // --- NOVO CAMPO ---
    @NotNull(message = "O tipo de remuneração é obrigatório")
    private TipoRemuneracao tipoRemuneracao;

    @NotNull(message = "O ID do estagiário é obrigatório")
    private Long estagiarioId;

    // Getters e Setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataTermino() { return dataTermino; }
    public void setDataTermino(LocalDate dataTermino) { this.dataTermino = dataTermino; }

    public Integer getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public TipoEstagio getTipoEstagio() { return tipoEstagio; }
    public void setTipoEstagio(TipoEstagio tipoEstagio) { this.tipoEstagio = tipoEstagio; }

    // Getter/Setter Novo
    public TipoRemuneracao getTipoRemuneracao() { return tipoRemuneracao; }
    public void setTipoRemuneracao(TipoRemuneracao tipoRemuneracao) { this.tipoRemuneracao = tipoRemuneracao; }

    public Long getEstagiarioId() { return estagiarioId; }
    public void setEstagiarioId(Long estagiarioId) { this.estagiarioId = estagiarioId; }
}