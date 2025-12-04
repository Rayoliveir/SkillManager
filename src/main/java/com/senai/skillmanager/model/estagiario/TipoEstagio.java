package com.senai.skillmanager.model.estagiario;

public enum TipoEstagio {
    OBRIGATORIO("Obrigatório"),
    NAO_OBRIGATORIO("Não Obrigatório");

    private String text;

    TipoEstagio(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
