package com.senai.skillmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardResponseDTO {

    private String nomeUsuario;
    private String descricaoPrincipal;

    // Campos para Supervisor/Coordenador
    private Integer totalEstagiarios;
    private List<String> listaNomesEstagiarios;

    // Campos para Estagiário (usando o seu DTO)
    private DashboardEstagiarioDTO dashboardEstagiario;

    // Campo antigo (que vamos deixar de usar para o estagiário)
    private List<AvaliacaoResponseDTO> avaliacoesRecentes;

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getDescricaoPrincipal() {
        return descricaoPrincipal;
    }

    public void setDescricaoPrincipal(String descricaoPrincipal) {
        this.descricaoPrincipal = descricaoPrincipal;
    }

    public Integer getTotalEstagiarios() {
        return totalEstagiarios;
    }

    public void setTotalEstagiarios(Integer totalEstagiarios) {
        this.totalEstagiarios = totalEstagiarios;
    }

    public List<String> getListaNomesEstagiarios() {
        return listaNomesEstagiarios;
    }

    public void setListaNomesEstagiarios(List<String> listaNomesEstagiarios) {
        this.listaNomesEstagiarios = listaNomesEstagiarios;
    }

    public DashboardEstagiarioDTO getDashboardEstagiario() {
        return dashboardEstagiario;
    }

    public void setDashboardEstagiario(DashboardEstagiarioDTO dashboardEstagiario) {
        this.dashboardEstagiario = dashboardEstagiario;
    }

    public List<AvaliacaoResponseDTO> getAvaliacoesRecentes() {
        return avaliacoesRecentes;
    }

    public void setAvaliacoesRecentes(List<AvaliacaoResponseDTO> avaliacoesRecentes) {
        this.avaliacoesRecentes = avaliacoesRecentes;
    }
}