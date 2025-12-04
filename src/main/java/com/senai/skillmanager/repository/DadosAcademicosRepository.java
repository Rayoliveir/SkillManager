package com.senai.skillmanager.repository;

import com.senai.skillmanager.model.estagiario.DadosAcademicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosAcademicosRepository extends JpaRepository<DadosAcademicos, Long> {
}