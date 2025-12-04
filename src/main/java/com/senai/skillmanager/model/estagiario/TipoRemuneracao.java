package com.senai.skillmanager.model.estagiario;

public enum TipoRemuneracao {
    REMUNERADO("Remunerado"),
    VOLUNTARIO("Voluntário");

    private final String texto;

    TipoRemuneracao(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}