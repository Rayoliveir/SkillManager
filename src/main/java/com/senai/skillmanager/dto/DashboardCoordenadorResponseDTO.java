package com.senai.skillmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardCoordenadorResponseDTO {

    private FaculdadeResponseDTO dadosFaculdade;
    private List<EstagiarioResponseDTO> estagiarios;

    // --- NOVOS CAMPOS DE ESTATÍSTICA ---
    private Integer totalEstagiarios;
    private Integer totalEmpresasParceiras; // Contagem de empresas distintas
    private Integer totalAvaliacoesRecebidas; // Total de avaliações dos alunos
    private Double mediaGeralNotas; // Média de todas as avaliações

    public FaculdadeResponseDTO getDadosFaculdade() {
        return dadosFaculdade;
    }

    public void setDadosFaculdade(FaculdadeResponseDTO dadosFaculdade) {
        this.dadosFaculdade = dadosFaculdade;
    }

    public List<EstagiarioResponseDTO> getEstagiarios() {
        return estagiarios;
    }

    public void setEstagiarios(List<EstagiarioResponseDTO> estagiarios) {
        this.estagiarios = estagiarios;
    }

    public Integer getTotalEstagiarios() {
        return totalEstagiarios;
    }

    public void setTotalEstagiarios(Integer totalEstagiarios) {
        this.totalEstagiarios = totalEstagiarios;
    }

    public Integer getTotalEmpresasParceiras() {
        return totalEmpresasParceiras;
    }

    public void setTotalEmpresasParceiras(Integer totalEmpresasParceiras) {
        this.totalEmpresasParceiras = totalEmpresasParceiras;
    }

    public Integer getTotalAvaliacoesRecebidas() {
        return totalAvaliacoesRecebidas;
    }

    public void setTotalAvaliacoesRecebidas(Integer totalAvaliacoesRecebidas) {
        this.totalAvaliacoesRecebidas = totalAvaliacoesRecebidas;
    }

    public Double getMediaGeralNotas() {
        return mediaGeralNotas;
    }

    public void setMediaGeralNotas(Double mediaGeralNotas) {
        this.mediaGeralNotas = mediaGeralNotas;
    }
}