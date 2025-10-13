package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.faculdade.Faculdade;
import java.time.YearMonth;

public class DadosAcademicosResponseDTO {
    private Long id;
    private Faculdade faculdade;
    private String curso;
    private String periodoSemestre;
    private YearMonth previsaoFormatura;
    private String ra;

    public DadosAcademicosResponseDTO() {
    }

    public DadosAcademicosResponseDTO(Long id, Faculdade faculdade, String curso, String periodoSemestre, YearMonth previsaoFormatura, String ra) {
        this.id = id;
        this.faculdade = faculdade;
        this.curso = curso;
        this.periodoSemestre = periodoSemestre;
        this.previsaoFormatura = previsaoFormatura;
        this.ra = ra;
    }

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