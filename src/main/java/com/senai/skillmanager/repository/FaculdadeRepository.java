package com.senai.skillmanager.repository;

import com.senai.skillmanager.model.faculdade.Faculdade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FaculdadeRepository extends JpaRepository<Faculdade, Long> {
    Optional<Faculdade> findByEmail(String email);
    Optional<Faculdade> findByCnpj(String cnpj);
}