package com.senai.skillmanager.repository;

import com.senai.skillmanager.model.estagiario.Estagiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstagiarioRepository extends JpaRepository<Estagiario, Long> {
    Optional<Estagiario> findByCpf(String cpf);
    Optional<Estagiario> findByEmail(String email);

    @Query("SELECT e FROM Estagiario e JOIN e.dadosAcademicos da WHERE da.faculdade.id = :faculdadeId")
    List<Estagiario> findByDadosAcademicos_Faculdade_Id(Long faculdadeId);

    @Query("SELECT e FROM Estagiario e WHERE e.empresa.id = :empresaId")
    List<Estagiario> findByEmpresaId(Long empresaId);
}