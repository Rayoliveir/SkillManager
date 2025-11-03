package com.senai.skillmanager.repository;

import com.senai.skillmanager.model.estagiario.DadosEstagio;
import com.senai.skillmanager.model.estagiario.Estagiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DadosEstagioRepository extends JpaRepository<DadosEstagio, Long> {
    List<DadosEstagio> findByEstagiario(Estagiario estagiario);
}