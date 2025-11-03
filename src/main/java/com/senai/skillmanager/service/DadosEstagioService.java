package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.DadosEstagioDTO;
import com.senai.skillmanager.dto.DadosEstagioResponseDTO;
import com.senai.skillmanager.model.estagiario.DadosEstagio;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.repository.DadosEstagioRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DadosEstagioService {

    private final DadosEstagioRepository dadosEstagioRepository;
    private final SupervisorRepository supervisorRepository;
    private final EstagiarioRepository estagiarioRepository;

    private final SupervisorService supervisorService;
    private final EstagiarioService estagiarioService;

    public DadosEstagioService(DadosEstagioRepository dadosEstagioRepository,
                               SupervisorRepository supervisorRepository,
                               EstagiarioRepository estagiarioRepository,
                               @Lazy SupervisorService supervisorService,
                               @Lazy EstagiarioService estagiarioService) {
        this.dadosEstagioRepository = dadosEstagioRepository;
        this.supervisorRepository = supervisorRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.supervisorService = supervisorService;
        this.estagiarioService = estagiarioService;
    }

    @Transactional
    public DadosEstagioResponseDTO salvar(DadosEstagioDTO dto, Authentication authentication) {
        String supervisorEmail = authentication.getName();
        Supervisor supervisor = supervisorRepository.findByEmail(supervisorEmail)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor logado não encontrado com email: " + supervisorEmail));

        Estagiario estagiario = estagiarioRepository.findById(dto.getEstagiarioId())
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado com ID: " + dto.getEstagiarioId()));

        if (!estagiario.getEmpresa().getId().equals(supervisor.getEmpresa().getId())) {
            throw new SecurityException("Acesso negado: O estagiário não pertence à empresa deste supervisor.");
        }

        DadosEstagio dadosEstagio = new DadosEstagio();
        dadosEstagio.setTitulo(dto.getTitulo());
        dadosEstagio.setTipo(dto.getTipo());
        dadosEstagio.setCargaHorariaSemanal(dto.getCargaHorariaSemanal());
        dadosEstagio.setIsRemunerado(dto.getIsRemunerado());
        dadosEstagio.setDataInicio(dto.getDataInicio());
        dadosEstagio.setDataTermino(dto.getDataTermino());
        dadosEstagio.setObservacoes(dto.getObservacoes());
        dadosEstagio.setSupervisor(supervisor);
        dadosEstagio.setEstagiario(estagiario);

        DadosEstagio dadosEstagioSalvo = dadosEstagioRepository.save(dadosEstagio);

        return toResponseDTO(dadosEstagioSalvo);
    }

    public List<DadosEstagioResponseDTO> listarTodos() {
        return dadosEstagioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DadosEstagioResponseDTO buscarPorId(Long id) {
        DadosEstagio dadosEstagio = dadosEstagioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dados de estágio não encontrados com ID: " + id));
        return toResponseDTO(dadosEstagio);
    }

    @Transactional
    public DadosEstagioResponseDTO atualizar(Long id, DadosEstagioDTO dto, Authentication authentication) {
        DadosEstagio dadosEstagioExistente = dadosEstagioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dados de estágio não encontrados com ID: " + id));

        String supervisorEmail = authentication.getName();
        Supervisor supervisor = supervisorRepository.findByEmail(supervisorEmail)
                .orElseThrow(() -> new EntityNotFoundException("Supervisor logado não encontrado com email: " + supervisorEmail));

        Estagiario estagiario = estagiarioRepository.findById(dto.getEstagiarioId())
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado com ID: " + dto.getEstagiarioId()));

        if (!estagiario.getEmpresa().getId().equals(supervisor.getEmpresa().getId())) {
            throw new SecurityException("Acesso negado: O estagiário não pertence à empresa deste supervisor.");
        }

        if (!dadosEstagioExistente.getSupervisor().getId().equals(supervisor.getId())) {
            throw new SecurityException("Acesso negado: Você não é o supervisor responsável por este contrato de estágio.");
        }

        dadosEstagioExistente.setTitulo(dto.getTitulo());
        dadosEstagioExistente.setTipo(dto.getTipo());
        dadosEstagioExistente.setCargaHorariaSemanal(dto.getCargaHorariaSemanal());
        dadosEstagioExistente.setIsRemunerado(dto.getIsRemunerado());
        dadosEstagioExistente.setDataInicio(dto.getDataInicio());
        dadosEstagioExistente.setDataTermino(dto.getDataTermino());
        dadosEstagioExistente.setObservacoes(dto.getObservacoes());
        dadosEstagioExistente.setSupervisor(supervisor);
        dadosEstagioExistente.setEstagiario(estagiario);

        DadosEstagio dadosEstagioAtualizado = dadosEstagioRepository.save(dadosEstagioExistente);
        return toResponseDTO(dadosEstagioAtualizado);
    }

    public void excluir(Long id) {
        if (!dadosEstagioRepository.existsById(id)) {
            throw new EntityNotFoundException("Dados de estágio não encontrados com ID: " + id);
        }
        dadosEstagioRepository.deleteById(id);
    }

    private DadosEstagioResponseDTO toResponseDTO(DadosEstagio dadosEstagio) {
        DadosEstagioResponseDTO dto = new DadosEstagioResponseDTO();
        dto.setId(dadosEstagio.getId());
        dto.setTitulo(dadosEstagio.getTitulo());
        dto.setTipo(dadosEstagio.getTipo());
        dto.setCargaHorariaSemanal(dadosEstagio.getCargaHorariaSemanal());
        dto.setIsRemunerado(dadosEstagio.getIsRemunerado());
        dto.setDataInicio(dadosEstagio.getDataInicio());
        dto.setDataTermino(dadosEstagio.getDataTermino());
        dto.setObservacoes(dadosEstagio.getObservacoes());
        dto.setSupervisor(supervisorService.toResponseDTO(dadosEstagio.getSupervisor()));
        dto.setEstagiario(estagiarioService.toResponseDTO(dadosEstagio.getEstagiario()));
        return dto;
    }
}