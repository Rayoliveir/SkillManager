package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.funcionario.Cargo;
import jakarta.validation.constraints.*;

public class FuncionarioDTO {
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

    // ----- MUDANÇA PRINCIPAL AQUI -----
    @NotBlank(message = "O CNPJ da empresa é obrigatório.")
    @Pattern(regexp = "^[0-9]{14}$", message = "O CNPJ deve ter 14 dígitos numéricos.")
    private String empresaCnpj; // Trocado de Long empresaId para String empresaCnpj

    // Getters e Setters atualizados
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
}