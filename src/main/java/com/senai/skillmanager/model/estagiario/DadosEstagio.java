package com.senai.skillmanager.model.estagiario;

import com.senai.skillmanager.model.empresa.Supervisor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "tab_dados_estagio")
public class DadosEstagio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O título do estágio é obrigatório.")
    private String titulo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo de estágio é obrigatório.")
    private TipoEstagio tipo;

    @Column(nullable = false)
    @NotNull(message = "A carga horária é obrigatória.")
    @Min(value = 1, message = "A carga horária deve ser maior que 0.")
    private int cargaHorariaSemanal;

    @Column(nullable = false)
    @NotNull(message = "O status de remuneração é obrigatório.")
    private boolean isRemunerado;

    @Column(nullable = false)
    @NotNull(message = "A data de início é obrigatória.")
    private LocalDate dataInicio;

    @Column(nullable = false)
    @NotNull(message = "A data de término é obrigatória.")
    private LocalDate dataTermino;

    @ManyToOne
    @JoinColumn(name = "supervisor_id", nullable = false)
    private Supervisor supervisor;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "estagiario_id", nullable = false)
    private Estagiario estagiario;

    public DadosEstagio() {
    }

    // Getters e Setters para todos os campos...
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

    public boolean getIsRemunerado() {
        return isRemunerado;
    }

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

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Estagiario getEstagiario() {
        return estagiario;
    }

    public void setEstagiario(Estagiario estagiario) {
        this.estagiario = estagiario;
    }
}