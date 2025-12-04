package com.senai.skillmanager.repository;

import com.senai.skillmanager.model.avaliacao.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByEstagiarioId(Long estagiarioId);

    // NOVO: Conta avaliações feitas por um supervisor
    long countBySupervisorId(Long supervisorId);
}