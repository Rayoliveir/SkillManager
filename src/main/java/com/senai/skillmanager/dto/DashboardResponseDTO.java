package com.senai.skillmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardResponseDTO {

    // --- MUDANÇA: Campos antigos removidos ---
    // O front-end espera estes dados DENTRO dos DTOs aninhados
    // private String nomeUsuario;
    // private String descricaoPrincipal;
    // private Integer totalEstagiarios;
    // private List<String> listaNomesEstagiarios;
    // private List<AvaliacaoResponseDTO> avaliacoesRecentes;

    // --- INFO: O DTO do Estagiário já estava correto ---
    private DashboardEstagiarioDTO dashboardEstagiario;

    // --- NOVO: DTOs aninhados para Supervisor e Coordenador ---
    // Estes DTOs corrigem o bug "Nenhum dado encontrado."
    private DashboardSupervisorResponseDTO dashboardSupervisor;
    private DashboardCoordenadorResponseDTO dashboardCoordenador;

    // Getters e Setters

    public DashboardEstagiarioDTO getDashboardEstagiario() {
        return dashboardEstagiario;
    }

    public void setDashboardEstagiario(DashboardEstagiarioDTO dashboardEstagiario) {
        this.dashboardEstagiario = dashboardEstagiario;
    }

    public DashboardSupervisorResponseDTO getDashboardSupervisor() {
        return dashboardSupervisor;
    }

    public void setDashboardSupervisor(DashboardSupervisorResponseDTO dashboardSupervisor) {
        this.dashboardSupervisor = dashboardSupervisor;
    }

    public DashboardCoordenadorResponseDTO getDashboardCoordenador() {
        return dashboardCoordenador;
    }

    public void setDashboardCoordenador(DashboardCoordenadorResponseDTO dashboardCoordenador) {
        this.dashboardCoordenador = dashboardCoordenador;
    }
}