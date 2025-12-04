package com.senai.skillmanager.model.estagiario;

import com.senai.skillmanager.model.empresa.Supervisor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "tab_dados_estagio")
public class DadosEstagio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório.")
    @Column(nullable = false)
    private String titulo;

    @NotNull(message = "A data de início é obrigatória.")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_termino")
    private LocalDate dataTermino;

    @NotNull(message = "A carga horária é obrigatória.")
    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @Column(name = "observacoes", columnDefinition = "TEXT") // TEXT permite textos longos
    private String observacoes;

    // --- MUDANÇA AQUI: Novo Enum para Remuneração ---
    @NotNull(message = "O tipo de remuneração é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_remuneracao", nullable = false)
    private TipoRemuneracao tipoRemuneracao;

    @NotNull(message = "O tipo de estágio é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_estagio", nullable = false)
    private TipoEstagio tipoEstagio;

    @ManyToOne
    @JoinColumn(name = "supervisor_id", nullable = false)
    private Supervisor supervisor;

    @OneToOne
    @JoinColumn(name = "estagiario_id", nullable = false, unique = true)
    private Estagiario estagiario;

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

    // Getter/Setter do Novo Enum
    public TipoRemuneracao getTipoRemuneracao() { return tipoRemuneracao; }
    public void setTipoRemuneracao(TipoRemuneracao tipoRemuneracao) { this.tipoRemuneracao = tipoRemuneracao; }

    public TipoEstagio getTipoEstagio() { return tipoEstagio; }
    public void setTipoEstagio(TipoEstagio tipoEstagio) { this.tipoEstagio = tipoEstagio; }

    public Supervisor getSupervisor() { return supervisor; }
    public void setSupervisor(Supervisor supervisor) { this.supervisor = supervisor; }

    public Estagiario getEstagiario() { return estagiario; }
    public void setEstagiario(Estagiario estagiario) { this.estagiario = estagiario; }
}