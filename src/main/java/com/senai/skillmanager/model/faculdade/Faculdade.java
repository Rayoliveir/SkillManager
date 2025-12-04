package com.senai.skillmanager.model.faculdade;

import com.senai.skillmanager.model.Endereco;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "tab_faculdades")
public class Faculdade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[0-9]{14}$", message = "O CNPJ deve ter 14 dígitos numéricos.")
    @NotBlank(message = "O CNPJ é obrigatório.")
    private String cnpj;

    @Column(nullable = false)
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve ter 10 ou 11 dígitos.")
    private String telefone;

    @Column
    private String site;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "faculdade")
    private List<Coordenador> coordenadores;

    public Faculdade() {
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Coordenador> getCoordenadores() {
        return coordenadores;
    }

    public void setCoordenadores(List<Coordenador> coordenadores) {
        this.coordenadores = coordenadores;
    }
}