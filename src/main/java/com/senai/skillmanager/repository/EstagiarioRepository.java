package com.senai.skillmanager.repository;

import com.senai.skillmanager.model.estagiario.Estagiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstagiarioRepository extends JpaRepository<Estagiario, Long> {

    Optional<Estagiario> findByEmail(String email);

    // Método para o Dashboard do Supervisor (busca pela empresa)
    List<Estagiario> findByEmpresaId(Long empresaId);

    // --- MÉTODOS QUE FALTAVAM (Causa do Erro de Compilação) ---
    // Busca estagiário pelo ID dos dados acadêmicos (se necessário)
    Optional<Estagiario> findByDadosAcademicos_Id(Long id);

    // Busca lista de estagiários por Faculdade (Para o Dashboard do Coordenador)
    List<Estagiario> findByDadosAcademicos_Faculdade_Id(Long faculdadeId);
    // -----------------------------------------------------------

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}