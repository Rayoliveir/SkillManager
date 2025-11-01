package com.senai.skillmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.YearMonth;

public class DadosAcademicosDTO {
    @NotNull(message = "O ID da faculdade é obrigatório.")
    private Long faculdadeId;

    @NotBlank(message = "O curso é obrigatório.")
    private String curso;

    @NotBlank(message = "O período/semestre é obrigatório.")
    private String periodoSemestre;

    @NotNull(message = "A previsão de formatura é obrigatória.")
    private YearMonth previsaoFormatura;

    @NotBlank(message = "O R.A. é obrigatório.")
    @Pattern(regexp = "\\d{6,12}", message = "O RA deve conter apenas dígitos numéricos (6 a 12).")
    private String ra;

    // Getters e Setters...
    public Long getFaculdadeId() {
        return faculdadeId;
    }

    public void setFaculdadeId(Long faculdadeId) {
        this.faculdadeId = faculdadeId;
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