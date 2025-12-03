package com.senai.skillmanager.model.empresa;

public enum TipoEmpresa {
    COMERCIO("Comércio"),
    SERVICO("Serviço"),
    COMERCIO_E_SERVICO("Comércio e serviço");

    private String text;

    TipoEmpresa(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}