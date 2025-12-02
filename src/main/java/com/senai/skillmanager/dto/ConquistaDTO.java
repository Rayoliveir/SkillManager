package com.senai.skillmanager.dto;

public class ConquistaDTO {
    private String nome;
    private String descricao;
    private String icone; // Vamos mandar emojis ou nomes de ícones

    public ConquistaDTO(String nome, String descricao, String icone) {
        this.nome = nome;
        this.descricao = descricao;
        this.icone = icone;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getIcone() { return icone; }
    public void setIcone(String icone) { this.icone = icone; }
}