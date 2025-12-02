package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.estagiario.TipoEstagio;
import com.senai.skillmanager.model.estagiario.TipoRemuneracao; // Import novo
import java.time.LocalDate;

public class DadosEstagioResponseDTO {

    private Long id;
    private String titulo;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private Integer cargaHoraria;
    private String observacoes;
    private TipoEstagio tipoEstagio;
    private TipoRemuneracao tipoRemuneracao; // --- NOVO CAMPO ---

    // Mantemos Supervisor e Estagiario aqui, mas no Service vamos setar null ou simplificado
    // para evitar o loop infinito, conforme corrigimos antes.
    private SupervisorResponseDTO supervisor;
    private EstagiarioResponseDTO estagiario;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    // Getter e Setter do Novo Enum
    public TipoRemuneracao getTipoRemuneracao() { return tipoRemuneracao; }
    public void setTipoRemuneracao(TipoRemuneracao tipoRemuneracao) { this.tipoRemuneracao = tipoRemuneracao; }

    public SupervisorResponseDTO getSupervisor() { return supervisor; }
    public void setSupervisor(SupervisorResponseDTO supervisor) { this.supervisor = supervisor; }

    public EstagiarioResponseDTO getEstagiario() { return estagiario; }
    public void setEstagiario(EstagiarioResponseDTO estagiario) { this.estagiario = estagiario; }
}