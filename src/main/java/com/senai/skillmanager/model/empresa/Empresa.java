package com.senai.skillmanager.model.empresa;

import com.senai.skillmanager.model.Endereco;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "tab_empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O nome da empresa é obrigatório.")
    private String nome;

    @Column(nullable = false)
    @NotBlank(message = "A razão social é obrigatória.")
    private String razaoSocial;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[0-9]{14}$", message = "O CNPJ deve ter 14 dígitos numéricos.")
    @NotBlank(message = "O CNPJ é obrigatório.")
    private String cnpj;

    // --- NOVOS CAMPOS ADICIONADOS ---

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "O tipo da empresa é obrigatório.")
    private TipoEmpresa tipoEmpresa;

    @Column(nullable = true)
    private String inscricaoEstadual;

    @Column(nullable = true)
    private String inscricaoMunicipal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    // ---------------------------------

    @OneToMany(mappedBy = "empresa")
    private List<Supervisor> supervisores;

    public Empresa() {
    }

    // Getters e Setters
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
    public String getRazaoSocial() {
        return razaoSocial;
    }
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }
    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }
    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }
    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }
    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }
    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    public List<Supervisor> getSupervisores() {
        return supervisores;
    }
    public void setSupervisores(List<Supervisor> supervisores) {
        this.supervisores = supervisores;
    }
}