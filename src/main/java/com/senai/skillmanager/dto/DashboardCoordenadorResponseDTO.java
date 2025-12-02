package com.senai.skillmanager.dto;

import java.util.List;

public class DashboardCoordenadorResponseDTO {

    private FaculdadeResponseDTO dadosFaculdade;

    // --- ESTE É O CAMPO QUE FALTAVA ---
    private CoordenadorResponseDTO dadosCoordenador;

    private List<EstagiarioResponseDTO> estagiarios;
    private Integer totalEstagiarios;
    private Integer totalEmpresasParceiras;
    private Integer totalAvaliacoesRecebidas;
    private Double mediaGeralNotas;

    // Getters e Setters

    public FaculdadeResponseDTO getDadosFaculdade() {
        return dadosFaculdade;
    }

    public void setDadosFaculdade(FaculdadeResponseDTO dadosFaculdade) {
        this.dadosFaculdade = dadosFaculdade;
    }

    // --- ESTE É O MÉTODO QUE O ERRO DIZ QUE NÃO EXISTE ---
    public CoordenadorResponseDTO getDadosCoordenador() {
        return dadosCoordenador;
    }

    public void setDadosCoordenador(CoordenadorResponseDTO dadosCoordenador) {
        this.dadosCoordenador = dadosCoordenador;
    }
    // -----------------------------------------------------

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