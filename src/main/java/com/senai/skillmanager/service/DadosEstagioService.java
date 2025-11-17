package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.*;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.empresa.Empresa;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.DadosEstagio;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.model.faculdade.Faculdade;
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
    private final SupervisorService supervisorService;
    private final EstagiarioService estagiarioService;

    public DadosEstagioService(DadosEstagioRepository dadosEstagioRepository,
                               SupervisorRepository supervisorRepository,
                               EstagiarioRepository estagiarioRepository,
                               CoordenadorRepository coordenadorRepository,
                               SupervisorService supervisorService,
                               EstagiarioService estagiarioService) {
        this.dadosEstagioRepository = dadosEstagioRepository;
        this.supervisorRepository = supervisorRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.coordenadorRepository = coordenadorRepository;
        this.supervisorService = supervisorService;
        this.estagiarioService = estagiarioService;
    }

    @Transactional
    public DadosEstagioResponseDTO salvar(DadosEstagioDTO dto, Authentication authentication) {
        String supervisorEmail = authentication.getName();
        Supervisor supervisor = supervisorRepository.findByEmail(supervisorEmail)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado com email: " + supervisorEmail));

        Estagiario estagiario = estagiarioRepository.findById(dto.getEstagiarioId())
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado com ID: " + dto.getEstagiarioId()));

        if (!Objects.equals(estagiario.getEmpresa().getId(), supervisor.getEmpresa().getId())) {
            throw new SecurityException("Acesso negado: O estagiário não pertence à empresa deste supervisor.");
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
        checkOwnership(dadosEstagio, authentication);

        String supervisorEmail = authentication.getName();
        Supervisor supervisor = supervisorRepository.findByEmail(supervisorEmail)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado com email: " + supervisorEmail));

        Estagiario estagiario = estagiarioRepository.findById(dto.getEstagiarioId())
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado com ID: " + dto.getEstagiarioId()));

        if (!Objects.equals(estagiario.getEmpresa().getId(), supervisor.getEmpresa().getId())) {
            throw new SecurityException("Acesso negado: O estagiário não pertence à empresa deste supervisor.");
        }

        dadosEstagio.setTitulo(dto.getTitulo());
        dadosEstagio.setDataInicio(dto.getDataInicio());
        dadosEstagio.setDataTermino(dto.getDataTermino());
        dadosEstagio.setCargaHoraria(dto.getCargaHoraria());
        dadosEstagio.setTipoEstagio(dto.getTipoEstagio());
        dadosEstagio.setSupervisor(supervisor);
        dadosEstagio.setEstagiario(estagiario);

        DadosEstagio dadosAtualizados = dadosEstagioRepository.save(dadosEstagio);
        return toResponseDTO(dadosAtualizados);
    }

    @Transactional
    public void excluir(Long id) {
        if (!dadosEstagioRepository.existsById(id)) {
            throw new EntityNotFoundException("Dados de Estágio não encontrados com ID: " + id);
        }
        dadosEstagioRepository.deleteById(id);
    }

    private DadosEstagio buscarEntidadePorId(Long id) {
        return dadosEstagioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dados de Estágio não encontrados com ID: " + id));
    }

    private void checkOwnership(DadosEstagio dadosEstagio, Authentication authentication) {
        String authEmail = authentication.getName();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return;
        }

        Estagiario estagiario = dadosEstagio.getEstagiario();
        Supervisor supervisor = dadosEstagio.getSupervisor();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"))) {
            if (estagiario.getEmail().equals(authEmail)) {
                return;
            }
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GERENTE"))) {
            if (supervisor.getEmail().equals(authEmail)) {
                return;
            }
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_FACULDADE"))) {
            Coordenador coordenador = coordenadorRepository.findByEmail(authEmail)
                    .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado."));

            if (Objects.equals(coordenador.getFaculdade().getId(), estagiario.getDadosAcademicos().getFaculdade().getId())) {
                return;
            }
        }

        throw new SecurityException("Acesso negado. Você não tem permissão para acessar este recurso.");
    }

    private DadosEstagio toEntity(DadosEstagioDTO dto) {
        DadosEstagio dadosEstagio = new DadosEstagio();
        dadosEstagio.setTitulo(dto.getTitulo());
        dadosEstagio.setDataInicio(dto.getDataInicio());
        dadosEstagio.setDataTermino(dto.getDataTermino());
        dadosEstagio.setCargaHoraria(dto.getCargaHoraria());
        dadosEstagio.setTipoEstagio(dto.getTipoEstagio());
        return dadosEstagio;
    }

    private DadosEstagioResponseDTO toResponseDTO(DadosEstagio entity) {
        DadosEstagioResponseDTO dto = new DadosEstagioResponseDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDataInicio(entity.getDataInicio());
        dto.setDataTermino(entity.getDataTermino());
        dto.setCargaHoraria(entity.getCargaHoraria());
        dto.setTipoEstagio(entity.getTipoEstagio());
        dto.setSupervisor(supervisorService.toResponseDTO(entity.getSupervisor()));
        dto.setEstagiario(estagiarioService.toResponseDTO(entity.getEstagiario()));
        return dto;
    }
}