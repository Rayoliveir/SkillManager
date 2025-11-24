package com.senai.skillmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DadosAcademicosResponseDTO {

    private Long id;
    private FaculdadeResponseDTO faculdade;
    private String curso;
    private String periodoSemestre;
    private String previsaoFormatura;
    private String ra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FaculdadeResponseDTO getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(FaculdadeResponseDTO faculdade) {
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

    public String getPrevisaoFormatura() {
        return previsaoFormatura;
    }

    public void setPrevisaoFormatura(String previsaoFormatura) {
        this.previsaoFormatura = previsaoFormatura;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }
}