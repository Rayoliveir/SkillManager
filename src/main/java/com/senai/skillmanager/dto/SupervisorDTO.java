package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.empresa.Cargo;
import com.senai.skillmanager.model.empresa.TipoEmpresa;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SupervisorDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    // --- CORREÇÃO: Senha opcional na edição ---
    @Pattern(regexp = "^$|.{6,}", message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;
    // ------------------------------------------

    private Cargo cargo;

    // Dados da Empresa (Para criar ou vincular)
    @NotBlank(message = "O CNPJ da empresa é obrigatório")
    private String empresaCnpj;

    private String empresaNome;
    private String empresaRazaoSocial;
    private TipoEmpresa empresaTipo;
    private String empresaInscricaoEstadual;
    private String empresaInscricaoMunicipal;
    private EnderecoDTO empresaEndereco;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }

    public String getEmpresaCnpj() { return empresaCnpj; }
    public void setEmpresaCnpj(String empresaCnpj) { this.empresaCnpj = empresaCnpj; }

    public String getEmpresaNome() { return empresaNome; }
    public void setEmpresaNome(String empresaNome) { this.empresaNome = empresaNome; }

    public String getEmpresaRazaoSocial() { return empresaRazaoSocial; }
    public void setEmpresaRazaoSocial(String empresaRazaoSocial) { this.empresaRazaoSocial = empresaRazaoSocial; }

    public TipoEmpresa getEmpresaTipo() { return empresaTipo; }
    public void setEmpresaTipo(TipoEmpresa empresaTipo) { this.empresaTipo = empresaTipo; }

    public String getEmpresaInscricaoEstadual() { return empresaInscricaoEstadual; }
    public void setEmpresaInscricaoEstadual(String empresaInscricaoEstadual) { this.empresaInscricaoEstadual = empresaInscricaoEstadual; }

    public String getEmpresaInscricaoMunicipal() { return empresaInscricaoMunicipal; }
    public void setEmpresaInscricaoMunicipal(String empresaInscricaoMunicipal) { this.empresaInscricaoMunicipal = empresaInscricaoMunicipal; }

    public EnderecoDTO getEmpresaEndereco() { return empresaEndereco; }
    public void setEmpresaEndereco(EnderecoDTO empresaEndereco) { this.empresaEndereco = empresaEndereco; }
}