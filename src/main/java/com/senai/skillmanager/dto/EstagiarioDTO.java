package com.senai.skillmanager.dto;

import com.senai.skillmanager.model.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class EstagiarioDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    @NotNull(message = "O gênero é obrigatório")
    private Genero genero;

    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @Pattern(regexp = "^$|.{6,}", message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;

    // --- ALTERAÇÃO AQUI: ID -> CÓDIGO ---
    @NotBlank(message = "O código da empresa é obrigatório")
    private String codigoEmpresa;
    // ------------------------------------

    private EnderecoDTO endereco;

    private DadosAcademicosDTO dadosAcademicos;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    // Getter e Setter atualizados
    public String getCodigoEmpresa() { return codigoEmpresa; }
    public void setCodigoEmpresa(String codigoEmpresa) { this.codigoEmpresa = codigoEmpresa; }

    public EnderecoDTO getEndereco() { return endereco; }
    public void setEndereco(EnderecoDTO endereco) { this.endereco = endereco; }

    public DadosAcademicosDTO getDadosAcademicos() { return dadosAcademicos; }
    public void setDadosAcademicos(DadosAcademicosDTO dadosAcademicos) { this.dadosAcademicos = dadosAcademicos; }
}