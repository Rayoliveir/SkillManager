package com.senai.skillmanager.model.empresa;

public enum Cargo {
    ADMIN("Administrador"),
    SUPERVISOR("Supervisor"),
    GERENTE("Gerente");

    private String text;

    Cargo(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
