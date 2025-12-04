package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.*;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.DadosEstagio;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.DadosEstagioRepository;
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
public class DadosEstagioService {

    private final DadosEstagioRepository dadosEstagioRepository;
    private final SupervisorRepository supervisorRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final CoordenadorRepository coordenadorRepository;

    public DadosEstagioService(DadosEstagioRepository dadosEstagioRepository,
                               SupervisorRepository supervisorRepository,
                               EstagiarioRepository estagiarioRepository,
                               CoordenadorRepository coordenadorRepository) {
        this.dadosEstagioRepository = dadosEstagioRepository;
        this.supervisorRepository = supervisorRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.coordenadorRepository = coordenadorRepository;
    }

    @Transactional
    public DadosEstagioResponseDTO salvar(DadosEstagioDTO dto, Authentication authentication) {
        String supervisorEmail = authentication.getName();
        Supervisor supervisor = supervisorRepository.findByEmail(supervisorEmail)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado."));

        Estagiario estagiario = estagiarioRepository.findById(dto.getEstagiarioId())
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado."));

        // --- PROTEÇÃO CONTRA DUPLICIDADE ---
        // Verifica se já existe um registro de estágio para este aluno
        if (dadosEstagioRepository.findByEstagiarioId(estagiario.getId()).isPresent()) {
            throw new IllegalArgumentException("Este estagiário já possui dados de estágio cadastrados. Utilize a função de editar.");
        }
        // -----------------------------------

        // Validação de Empresa (Supervisor só cadastra para estagiário da mesma empresa)
        if (estagiario.getEmpresa() == null || supervisor.getEmpresa() == null ||
                !Objects.equals(estagiario.getEmpresa().getId(), supervisor.getEmpresa().getId())) {
            throw new SecurityException("Acesso negado: O estagiário pertence a uma empresa diferente da sua.");
        }

        DadosEstagio dadosEstagio = toEntity(dto);
        dadosEstagio.setSupervisor(supervisor);
        dadosEstagio.setEstagiario(estagiario);

        DadosEstagio dadosSalvos = dadosEstagioRepository.save(dadosEstagio);
        return toResponseDTO(dadosSalvos);
    }

    public List<DadosEstagioResponseDTO> listarTodos() {
        return dadosEstagioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DadosEstagioResponseDTO buscarPorId(Long id, Authentication authentication) {
        DadosEstagio dadosEstagio = buscarEntidadePorId(id);
        checkOwnership(dadosEstagio, authentication);
        return toResponseDTO(dadosEstagio);
    }

    @Transactional
    public DadosEstagioResponseDTO atualizar(Long id, DadosEstagioDTO dto, Authentication authentication) {
        DadosEstagio dadosEstagio = buscarEntidadePorId(id);

        // Verifica se quem está tentando atualizar tem permissão
        checkOwnership(dadosEstagio, authentication);

        // Se for Supervisor, garante que é o supervisor da empresa correta
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GERENTE"))) {

            String email = authentication.getName();
            Supervisor supervisor = supervisorRepository.findByEmail(email).orElseThrow();

            if (!Objects.equals(supervisor.getEmpresa().getId(), dadosEstagio.getEstagiario().getEmpresa().getId())) {
                throw new SecurityException("Você não pode alterar dados de estágio de outra empresa.");
            }
        }

        // Atualiza os campos
        dadosEstagio.setTitulo(dto.getTitulo());
        dadosEstagio.setDataInicio(dto.getDataInicio());
        dadosEstagio.setDataTermino(dto.getDataTermino());
        dadosEstagio.setCargaHoraria(dto.getCargaHoraria());
        dadosEstagio.setObservacoes(dto.getObservacoes());
        dadosEstagio.setTipoEstagio(dto.getTipoEstagio());
        dadosEstagio.setTipoRemuneracao(dto.getTipoRemuneracao());

        DadosEstagio dadosAtualizados = dadosEstagioRepository.save(dadosEstagio);
        return toResponseDTO(dadosAtualizados);
    }

    @Transactional
    public void excluir(Long id) {
        if (!dadosEstagioRepository.existsById(id)) {
            throw new EntityNotFoundException("Dados de Estágio não encontrados.");
        }
        dadosEstagioRepository.deleteById(id);
    }

    private DadosEstagio buscarEntidadePorId(Long id) {
        return dadosEstagioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dados de Estágio não encontrados com ID: " + id));
    }

    // Validação de segurança de quem pode VER os dados
    private void checkOwnership(DadosEstagio dadosEstagio, Authentication authentication) {
        String authEmail = authentication.getName();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return;
        }

        // Se for o próprio Estagiário
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"))) {
            if (dadosEstagio.getEstagiario().getEmail().equals(authEmail)) {
                return;
            }
        }

        // Se for Supervisor/Gerente
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GERENTE"))) {

            Supervisor supervisor = supervisorRepository.findByEmail(authEmail)
                    .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado."));

            // Verifica se a empresa do supervisor é a mesma do estágio
            if (Objects.equals(supervisor.getEmpresa().getId(), dadosEstagio.getEstagiario().getEmpresa().getId())) {
                return;
            }
        }

        // Se for Faculdade (Coordenador)
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_FACULDADE"))) {
            Coordenador coordenador = coordenadorRepository.findByEmail(authEmail)
                    .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado."));

            if (dadosEstagio.getEstagiario().getDadosAcademicos() != null &&
                    Objects.equals(coordenador.getFaculdade().getId(), dadosEstagio.getEstagiario().getDadosAcademicos().getFaculdade().getId())) {
                return;
            }
        }

        throw new SecurityException("Acesso negado. Você não tem permissão para visualizar estes dados.");
    }

    private DadosEstagio toEntity(DadosEstagioDTO dto) {
        DadosEstagio dadosEstagio = new DadosEstagio();
        dadosEstagio.setTitulo(dto.getTitulo());
        dadosEstagio.setDataInicio(dto.getDataInicio());
        dadosEstagio.setDataTermino(dto.getDataTermino());
        dadosEstagio.setCargaHoraria(dto.getCargaHoraria());
        dadosEstagio.setObservacoes(dto.getObservacoes());
        dadosEstagio.setTipoEstagio(dto.getTipoEstagio());
        dadosEstagio.setTipoRemuneracao(dto.getTipoRemuneracao());
        return dadosEstagio;
    }

    private DadosEstagioResponseDTO toResponseDTO(DadosEstagio entity) {
        DadosEstagioResponseDTO dto = new DadosEstagioResponseDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDataInicio(entity.getDataInicio());
        dto.setDataTermino(entity.getDataTermino());
        dto.setCargaHoraria(entity.getCargaHoraria());
        dto.setObservacoes(entity.getObservacoes());
        dto.setTipoEstagio(entity.getTipoEstagio());
        dto.setTipoRemuneracao(entity.getTipoRemuneracao());

        // Previne loop infinito enviando nulo ou objeto simplificado
        dto.setEstagiario(null);
        dto.setSupervisor(null);

        return dto;
    }
}