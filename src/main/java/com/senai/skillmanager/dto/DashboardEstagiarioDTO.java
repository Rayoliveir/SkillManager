package com.senai.skillmanager.dto;

import java.util.List;

public class DashboardEstagiarioDTO {

    private EstagiarioResponseDTO dadosEstagiario;
    private List<AvaliacaoResponseDTO> avaliacoes;
    private List<CompetenciaDTO> competencias;

    // --- NOVO CAMPO ---
    private List<ConquistaDTO> conquistas;

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

    public List<CompetenciaDTO> getCompetencias() {
        return competencias;
    }

    public void setCompetencias(List<CompetenciaDTO> competencias) {
        this.competencias = competencias;
    }

    // --- Getter e Setter Novos ---
    public List<ConquistaDTO> getConquistas() {
        return conquistas;
    }

    public void setConquistas(List<ConquistaDTO> conquistas) {
        this.conquistas = conquistas;
    }
}