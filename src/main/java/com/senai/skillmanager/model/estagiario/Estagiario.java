package com.senai.skillmanager.model.estagiario;

import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.Genero;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tab_estagiario")
public class Estagiario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Column(nullable = false)
    @NotNull(message = "A data de nascimento é obrigatória.")
    private LocalDate dataNascimento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O gênero é obrigatório.")
    private Genero genero;

    @Column(nullable = false)
    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve ter 10 ou 11 dígitos.")
    private String telefone;

    @Column(nullable = false, unique = true)
    @Email(message = "Email inválido.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números.")
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @Column(nullable = false)
    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dados_academicos_id", referencedColumnName = "id")
    private DadosAcademicos dadosAcademicos;

    @OneToMany(mappedBy = "estagiario", cascade = CascadeType.ALL)
    private List<DadosEstagio> dadosEstagio;

    public Estagiario() {
    }

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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public DadosAcademicos getDadosAcademicos() {
        return dadosAcademicos;
    }

    public void setDadosAcademicos(DadosAcademicos dadosAcademicos) {
        this.dadosAcademicos = dadosAcademicos;
    }

    public List<DadosEstagio> getDadosEstagio() {
        return dadosEstagio;
    }

    public void setDadosEstagio(List<DadosEstagio> dadosEstagio) {
        this.dadosEstagio = dadosEstagio;
    }
}