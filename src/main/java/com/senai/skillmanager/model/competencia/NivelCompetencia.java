package com.senai.skillmanager.model.competencia;

public enum NivelCompetencia {
    INICIANTE ("Iniciante"),
    BASICO ("Básico"),
    INTERMEDIARIO ("Intermediário"),
    AVANCADO ("Avançado");

    private final String texto;

    NivelCompetencia(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}