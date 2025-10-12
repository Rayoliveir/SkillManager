package com.senai.skillmanager.model.avaliacao;

import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.funcionario.Funcionario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "tab_avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String feedbackPositivo;

    @Column(columnDefinition = "TEXT")
    private String pontosDeMelhoria;

    @Min(1)
    @Max(5)
    private Integer notaFrequencia;

    @Min(1)
    @Max(5)
    private Integer notaDesempenho;

    @Min(1)
    @Max(5)
    private Integer notaOrganizacao;

    @Min(1)
    @Max(5)
    private Integer notaParticipacao;

    @Min(1)
    @Max(5)
    private Integer notaComportamento;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataAvaliacao;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "supervisor_id", nullable = false)
    private Funcionario supervisor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "estagiario_id", nullable = false)
    private Estagiario estagiario;

    public Avaliacao() {
    }

    public Avaliacao(Long id, String titulo, String feedbackPositivo, String pontosDeMelhoria, Integer notaFrequencia, Integer notaDesempenho, Integer notaOrganizacao, Integer notaParticipacao, Integer notaComportamento, LocalDate dataAvaliacao, Funcionario supervisor, Estagiario estagiario) {
        this.id = id;
        this.titulo = titulo;
        this.feedbackPositivo = feedbackPositivo;
        this.pontosDeMelhoria = pontosDeMelhoria;
        this.notaFrequencia = notaFrequencia;
        this.notaDesempenho = notaDesempenho;
        this.notaOrganizacao = notaOrganizacao;
        this.notaParticipacao = notaParticipacao;
        this.notaComportamento = notaComportamento;
        this.dataAvaliacao = dataAvaliacao;
        this.supervisor = supervisor;
        this.estagiario = estagiario;
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

    public String getFeedbackPositivo() {
        return feedbackPositivo;
    }

    public void setFeedbackPositivo(String feedbackPositivo) {
        this.feedbackPositivo = feedbackPositivo;
    }

    public String getPontosDeMelhoria() {
        return pontosDeMelhoria;
    }

    public void setPontosDeMelhoria(String pontosDeMelhoria) {
        this.pontosDeMelhoria = pontosDeMelhoria;
    }

    public Integer getNotaFrequencia() {
        return notaFrequencia;
    }

    public void setNotaFrequencia(Integer notaFrequencia) {
        this.notaFrequencia = notaFrequencia;
    }

    public Integer getNotaDesempenho() {
        return notaDesempenho;
    }

    public void setNotaDesempenho(Integer notaDesempenho) {
        this.notaDesempenho = notaDesempenho;
    }

    public Integer getNotaOrganizacao() {
        return notaOrganizacao;
    }

    public void setNotaOrganizacao(Integer notaOrganizacao) {
        this.notaOrganizacao = notaOrganizacao;
    }

    public Integer getNotaParticipacao() {
        return notaParticipacao;
    }

    public void setNotaParticipacao(Integer notaParticipacao) {
        this.notaParticipacao = notaParticipacao;
    }

    public Integer getNotaComportamento() {
        return notaComportamento;
    }

    public void setNotaComportamento(Integer notaComportamento) {
        this.notaComportamento = notaComportamento;
    }

    public LocalDate getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDate dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public Funcionario getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Funcionario supervisor) {
        this.supervisor = supervisor;
    }

    public Estagiario getEstagiario() {
        return estagiario;
    }

    public void setEstagiario(Estagiario estagiario) {
        this.estagiario = estagiario;
    }
}