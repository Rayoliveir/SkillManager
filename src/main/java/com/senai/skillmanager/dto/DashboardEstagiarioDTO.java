package com.senai.skillmanager.dto;

import java.util.List;

public class DashboardEstagiarioDTO {

    private EstagiarioResponseDTO dadosEstagiario;
    private List<AvaliacaoResponseDTO> avaliacoes;

    public EstagiarioResponseDTO getDadosEstagiario() {
        return dadosEstagiario;
    }

    public void setDadosEstagiario(EstagiarioResponseDTO dadosEstagiario) {
        this.dadosEstagiario = dadosEstagiario;
    }

    public List<AvaliacaoResponseDTO> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoResponseDTO> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }
}