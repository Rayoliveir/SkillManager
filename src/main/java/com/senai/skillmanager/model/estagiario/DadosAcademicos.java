package com.senai.skillmanager.model.estagiario;

import com.senai.skillmanager.model.faculdade.Faculdade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.YearMonth;

@Entity
@Table(name = "tab_dados_academicos")
public class DadosAcademicos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "faculdade_id", nullable = false)
    private Faculdade faculdade;

    @NotBlank(message = "O curso é obrigatório.")
    @Column(nullable = false)
    private String curso;

    @NotBlank(message = "O período/semestre é obrigatório.")
    @Column(nullable = false)
    private String periodoSemestre;

    @NotNull(message = "A previsão de formatura é obrigatória.")
    @Column(nullable = false)
    private YearMonth previsaoFormatura;

    @NotBlank(message = "O R.A. é obrigatório.")
    @Column(nullable = false, unique = true)
    private String ra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Faculdade getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(Faculdade faculdade) {
        this.faculdade = faculdade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getPeriodoSemestre() {
        return periodoSemestre;
    }

    public void setPeriodoSemestre(String periodoSemestre) {
        this.periodoSemestre = periodoSemestre;
    }

    public YearMonth getPrevisaoFormatura() {
        return previsaoFormatura;
    }

    public void setPrevisaoFormatura(YearMonth previsaoFormatura) {
        this.previsaoFormatura = previsaoFormatura;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }
}