package com.senai.skillmanager.repository;

import com.senai.skillmanager.model.estagiario.Estagiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstagiarioRepository extends JpaRepository<Estagiario, Long> {
    Optional<Estagiario> findByEmail(String email);
    Optional<Estagiario> findByCpf(String cpf);
}