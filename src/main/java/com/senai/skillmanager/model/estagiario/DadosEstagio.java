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
    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column
    private LocalDate dataTermino;

    @NotNull(message = "A carga horária é obrigatória.")
    @Column(nullable = false)
    private Integer cargaHoraria;

    @NotNull(message = "O tipo de estágio é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEstagio tipoEstagio;

    @ManyToOne
    @JoinColumn(name = "supervisor_id", nullable = false)
    private Supervisor supervisor;

    @OneToOne
    @JoinColumn(name = "estagiario_id", nullable = false, unique = true)
    private Estagiario estagiario;

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