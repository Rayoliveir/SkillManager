package com.senai.skillmanager.dto;

public class DadosAcademicosResponseDTO {

    private Long id;
    private String ra;
    private String curso;
    private Integer periodoSemestre; // Alterado para Integer para evitar erro de tipo com o Banco
    private String previsaoFormatura; // Adicionado
    private FaculdadeResponseDTO faculdade; // Adicionado

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Integer getPeriodoSemestre() {
        return periodoSemestre;
    }

    public void setPeriodoSemestre(Integer periodoSemestre) {
        this.periodoSemestre = periodoSemestre;
    }

    public String getPrevisaoFormatura() {
        return previsaoFormatura;
    }

    public void setPrevisaoFormatura(String previsaoFormatura) {
        this.previsaoFormatura = previsaoFormatura;
    }

    public FaculdadeResponseDTO getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(FaculdadeResponseDTO faculdade) {
        this.faculdade = faculdade;
    }
}