package com.senai.skillmanager.model.estagiario;

import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.Genero;
import com.senai.skillmanager.model.empresa.Empresa;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "tab_estagiario")
public class Estagiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @NotNull(message = "O gênero é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @NotBlank(message = "O telefone é obrigatório.")
    @Column(nullable = false)
    private String telefone;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Email inválido.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "O CPF é obrigatório.")
    @Column(nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    @Column(nullable = false)
    private String senha;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id", nullable = false)
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dados_academicos_id", referencedColumnName = "id", nullable = false)
    private DadosAcademicos dadosAcademicos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public DadosAcademicos getDadosAcademicos() {
        return dadosAcademicos;
    }

    public void setDadosAcademicos(DadosAcademicos dadosAcademicos) {
        this.dadosAcademicos = dadosAcademicos;
    }
}