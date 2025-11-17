package com.senai.skillmanager.model.avaliacao;

import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.Estagiario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "tab_avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @Column
    private String feedback;

    @NotNull
    @Min(1) @Max(5)
    private Integer notaDesempenho;

    @NotNull
    @Min(1) @Max(5)
    private Integer notaHabilidadesTecnicas;

    @NotNull
    @Min(1) @Max(5)
    private Integer notaHabilidadesComportamentais;

    @NotNull
    private LocalDate dataAvaliacao;

    @ManyToOne
    @JoinColumn(name = "supervisor_id", nullable = false)
    private Supervisor supervisor;

    @ManyToOne
    @JoinColumn(name = "estagiario_id", nullable = false)
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