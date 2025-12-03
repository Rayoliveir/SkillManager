package com.senai.skillmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardSupervisorResponseDTO {

    private SupervisorResponseDTO dadosSupervisor;
    private List<EstagiarioResponseDTO> estagiarios;

    // --- NOVOS CAMPOS (Para corrigir o "0" no dashboard) ---
    private Integer totalEstagiarios;
    private Integer totalAvaliacoes;

    public SupervisorResponseDTO getDadosSupervisor() {
        return dadosSupervisor;
    }

    public void setDadosSupervisor(SupervisorResponseDTO dadosSupervisor) {
        this.dadosSupervisor = dadosSupervisor;
    }

    public List<EstagiarioResponseDTO> getEstagiarios() {
        return estagiarios;
    }

    public void setEstagiarios(List<EstagiarioResponseDTO> estagiarios) {
        this.estagiarios = estagiarios;
    }

    // --- Getters e Setters dos Novos Campos ---

    public Integer getTotalEstagiarios() {
        return totalEstagiarios;
    }

    public void setTotalEstagiarios(Integer totalEstagiarios) {
        this.totalEstagiarios = totalEstagiarios;
    }

    public Integer getTotalAvaliacoes() {
        return totalAvaliacoes;
    }

    public void setTotalAvaliacoes(Integer totalAvaliacoes) {
        this.totalAvaliacoes = totalAvaliacoes;
    }
}