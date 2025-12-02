package com.senai.skillmanager.model.competencia;

import com.senai.skillmanager.model.estagiario.Estagiario;
import jakarta.persistence.*;

@Entity
@Table(name = "tab_competencias")
public class Competencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome; // Ex: Java, Comunicação, Excel

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelCompetencia nivel; // Ex: AVANCADO

    @ManyToOne
    @JoinColumn(name = "estagiario_id", nullable = false)
    private Estagiario estagiario;

    // Construtores
    public Competencia() {}

    public Competencia(String nome, NivelCompetencia nivel, Estagiario estagiario) {
        this.nome = nome;
        this.nivel = nivel;
        this.estagiario = estagiario;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public NivelCompetencia getNivel() { return nivel; }
    public void setNivel(NivelCompetencia nivel) { this.nivel = nivel; }

    public Estagiario getEstagiario() { return estagiario; }
    public void setEstagiario(Estagiario estagiario) { this.estagiario = estagiario; }
}