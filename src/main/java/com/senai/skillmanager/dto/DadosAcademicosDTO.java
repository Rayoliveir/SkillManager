package com.senai.skillmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.YearMonth;

public class DadosAcademicosDTO {

    @NotBlank(message = "O CNPJ da faculdade é obrigatório.")
    @Pattern(regexp = "^[0-9]{14}$", message = "O CNPJ da faculdade deve ter 14 dígitos numéricos.")
    private String faculdadeCnpj;

    @NotBlank(message = "O curso é obrigatório.")
    private String curso;

    @NotBlank(message = "O período/semestre é obrigatório.")
    private String periodoSemestre;

    @NotNull(message = "A previsão de formatura é obrigatória.")
    private YearMonth previsaoFormatura;

    @NotBlank(message = "O R.A. é obrigatório.")
    @Pattern(regexp = "\\d{6,12}", message = "O RA deve conter apenas dígitos numéricos (6 a 12).")
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