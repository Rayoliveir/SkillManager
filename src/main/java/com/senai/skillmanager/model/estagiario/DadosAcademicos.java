package com.senai.skillmanager.model.estagiario;

import com.senai.skillmanager.model.faculdade.Faculdade;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.YearMonth;

@Entity
@Table(name = "tab_dados_academicos")
public class DadosAcademicos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "faculdade_id", nullable = false)
    @NotNull(message = "A faculdade é obrigatória.")
    private Faculdade faculdade; // NOME CORRIGIDO AQUI

    @Column(nullable = false)
    @NotBlank(message = "O curso é obrigatório.")
    private String curso;

    @Column(nullable = false)
    @NotBlank(message = "O período/semestre é obrigatório.")
    private String periodoSemestre;

    @Column(nullable = false)
    @NotNull(message = "A previsão de formatura é obrigatória.")
    private YearMonth previsaoFormatura;

    @Column(nullable = false)
    @NotBlank(message = "O R.A. é obrigatório.")
    @Pattern(regexp = "\\d{6,12}", message = "O RA deve conter apenas dígitos numéricos (6 a 12).")
    private String ra;

    public DadosAcademicos() {
    }

    public DadosAcademicos(Long id, Faculdade faculdade, String curso, String periodoSemestre, YearMonth previsaoFormatura, String ra) {
        this.id = id;
        this.faculdade = faculdade;
        this.curso = curso;
        this.periodoSemestre = periodoSemestre;
        this.previsaoFormatura = previsaoFormatura;
        this.ra = ra;
    }

    // Getters e Setters
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