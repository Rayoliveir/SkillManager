package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.empresa.Cargo;
import com.senai.skillmanager.model.empresa.TipoEmpresa; // Importa o Enum
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class SupervisorDTO {
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Digite um email válido.")
    private String email;

    @NotBlank(message = "Senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    @NotNull(message = "O cargo é obrigatório.")
    private Cargo cargo;

    // --- MUDANÇA CRUCIAL (BASEADA NA SUA IDEIA DE CNPJ) ---

    @NotBlank(message = "O CNPJ da empresa é obrigatório.")
    @Pattern(regexp = "^[0-9]{14}$", message = "O CNPJ deve ter 14 dígitos numéricos.")
    private String empresaCnpj;

    // --- CAMPOS OPCIONAIS DO FLUXO A (ATUALIZADOS) ---
    private String empresaNome;
    private String empresaRazaoSocial;

    // Novos campos da empresa
    private TipoEmpresa empresaTipo;
    private String empresaInscricaoEstadual;
    private String empresaInscricaoMunicipal;
    @Valid // Valida o endereço se ele for fornecido
    private EnderecoDTO empresaEndereco;

    // Getters e Setters
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public Cargo getCargo() {
        return cargo;
    }
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    public String getEmpresaCnpj() {
        return empresaCnpj;
    }
    public void setEmpresaCnpj(String empresaCnpj) {
        this.empresaCnpj = empresaCnpj;
    }
    public String getEmpresaNome() {
        return empresaNome;
    }
    public void setEmpresaNome(String empresaNome) {
        this.empresaNome = empresaNome;
    }
    public String getEmpresaRazaoSocial() {
        return empresaRazaoSocial;
    }
    public void setEmpresaRazaoSocial(String empresaRazaoSocial) {
        this.empresaRazaoSocial = empresaRazaoSocial;
    }
    public TipoEmpresa getEmpresaTipo() {
        return empresaTipo;
    }
    public void setEmpresaTipo(TipoEmpresa empresaTipo) {
        this.empresaTipo = empresaTipo;
    }
    public String getEmpresaInscricaoEstadual() {
        return empresaInscricaoEstadual;
    }
    public void setEmpresaInscricaoEstadual(String empresaInscricaoEstadual) {
        this.empresaInscricaoEstadual = empresaInscricaoEstadual;
    }
    public String getEmpresaInscricaoMunicipal() {
        return empresaInscricaoMunicipal;
    }
    public void setEmpresaInscricaoMunicipal(String empresaInscricaoMunicipal) {
        this.empresaInscricaoMunicipal = empresaInscricaoMunicipal;
    }
    public EnderecoDTO getEmpresaEndereco() {
        return empresaEndereco;
    }
    public void setEmpresaEndereco(EnderecoDTO empresaEndereco) {
        this.empresaEndereco = empresaEndereco;
    }
}