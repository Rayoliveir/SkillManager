package com.senai.skillmanager.repository;

import com.senai.skillmanager.model.faculdade.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
    Optional<Coordenador> findByEmail(String email);
}