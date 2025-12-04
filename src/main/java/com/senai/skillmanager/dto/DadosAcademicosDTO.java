package com.senai.skillmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class DadosAcademicosDTO {

    @NotBlank(message = "O CNPJ da faculdade é obrigatório.")
    private String faculdadeCnpj;

    @NotBlank(message = "O curso é obrigatório.")
    private String curso;

    @NotBlank(message = "O período/semestre é obrigatório.")
    private String periodoSemestre;

    @NotBlank(message = "A previsão de formatura é obrigatória.")
    @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "O formato da previsão de formatura deve ser YYYY-MM")
    private String previsaoFormatura;

    @NotBlank(message = "O R.A. é obrigatório.")
    private String ra;

    public String getFaculdadeCnpj() {
        return faculdadeCnpj;
    }

    public void setFaculdadeCnpj(String faculdadeCnpj) {
        this.faculdadeCnpj = faculdadeCnpj;
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