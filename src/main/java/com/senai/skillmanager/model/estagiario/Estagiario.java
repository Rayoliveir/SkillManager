package com.senai.skillmanager.model.estagiario;

import com.senai.skillmanager.model.empresa.Empresa;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.Genero;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tab_estagiario")
public class Estagiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    private String telefone;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dados_academicos_id", referencedColumnName = "id")
    private DadosAcademicos dadosAcademicos;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

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
    public Empresa getEmpresa() {
        return empresa;
    }
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}