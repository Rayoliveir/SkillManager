package com.senai.skillmanager.repository;

import com.senai.skillmanager.model.estagiario.DadosEstagio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DadosEstagioRepository extends JpaRepository<DadosEstagio, Long> {

    // Este é o método que o Service está tentando chamar e não encontra
    Optional<DadosEstagio> findByEstagiarioId(Long estagiarioId);
}