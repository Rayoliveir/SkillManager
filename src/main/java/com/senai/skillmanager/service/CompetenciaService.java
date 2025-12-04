package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.CompetenciaDTO;
import com.senai.skillmanager.model.competencia.Competencia;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.repository.CompetenciaRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompetenciaService {

    private final CompetenciaRepository competenciaRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final SupervisorRepository supervisorRepository;

    public CompetenciaService(CompetenciaRepository competenciaRepository,
                              EstagiarioRepository estagiarioRepository,
                              SupervisorRepository supervisorRepository) {
        this.competenciaRepository = competenciaRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.supervisorRepository = supervisorRepository;
    }

    @Transactional
    public CompetenciaDTO adicionar(CompetenciaDTO dto, Authentication authentication) {
        // 1. Identifica o Estagiário
        Estagiario estagiario = estagiarioRepository.findById(dto.getEstagiarioId())
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado com ID: " + dto.getEstagiarioId()));

        // 2. Verifica Segurança (Se quem está adicionando é Supervisor da mesma empresa)
        validarPermissaoSupervisor(estagiario, authentication);

        // 3. Cria a Entidade
        Competencia competencia = new Competencia();
        competencia.setNome(dto.getNome());
        competencia.setNivel(dto.getNivel());
        competencia.setEstagiario(estagiario);

        // 4. Salva
        Competencia salvo = competenciaRepository.save(competencia);
        return toDTO(salvo);
    }

    @Transactional
    public void remover(Long id, Authentication authentication) {
        Competencia competencia = competenciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competência não encontrada com ID: " + id));

        // Verifica se o supervisor tem permissão sobre o estagiário dono desta competência
        validarPermissaoSupervisor(competencia.getEstagiario(), authentication);

        competenciaRepository.delete(competencia);
    }

    public List<CompetenciaDTO> listarPorEstagiario(Long estagiarioId) {
        // Este método pode ser público (ex: Faculdade e Estagiário também podem ver)
        return competenciaRepository.findByEstagiarioId(estagiarioId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // --- Métodos Auxiliares ---

    private void validarPermissaoSupervisor(Estagiario estagiario, Authentication authentication) {
        // Se for Admin, passa direto
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return;
        }

        String emailLogado = authentication.getName();

        // Se for Supervisor, verifica a empresa
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GERENTE"))) {

            Supervisor supervisor = supervisorRepository.findByEmail(emailLogado)
                    .orElseThrow(() -> new SecurityException("Supervisor não encontrado."));

            if (!Objects.equals(supervisor.getEmpresa().getId(), estagiario.getEmpresa().getId())) {
                throw new SecurityException("Acesso negado: Você só pode gerenciar competências de estagiários da sua empresa.");
            }
        } else {
            // Outros perfis (Faculdade, Estagiário) não podem adicionar/remover competências neste fluxo
            throw new SecurityException("Acesso negado: Apenas Supervisores podem gerenciar competências.");
        }
    }

    private CompetenciaDTO toDTO(Competencia entity) {
        CompetenciaDTO dto = new CompetenciaDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setNivel(entity.getNivel());
        dto.setEstagiarioId(entity.getEstagiario().getId());
        return dto;
    }
}