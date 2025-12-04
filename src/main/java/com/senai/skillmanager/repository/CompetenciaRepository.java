package com.senai.skillmanager.repository;

import com.senai.skillmanager.model.competencia.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {
    // NOVO: Busca skills do aluno
    List<Competencia> findByEstagiarioId(Long estagiarioId);
}