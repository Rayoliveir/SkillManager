package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.AvaliacaoDTO;
import com.senai.skillmanager.dto.AvaliacaoResponseDTO;
import com.senai.skillmanager.model.avaliacao.Avaliacao;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.repository.AvaliacaoRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final SupervisorRepository supervisorRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final SupervisorService supervisorService;
    private final EstagiarioService estagiarioService;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository,
                            SupervisorRepository supervisorRepository,
                            EstagiarioRepository estagiarioRepository,
                            SupervisorService supervisorService,
                            EstagiarioService estagiarioService) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.supervisorRepository = supervisorRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.supervisorService = supervisorService;
        this.estagiarioService = estagiarioService;
    }

    @Transactional
    public AvaliacaoResponseDTO salvar(AvaliacaoDTO dto) {
        Supervisor supervisor = supervisorRepository.findById(dto.getSupervisorId())
                .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado com ID: " + dto.getSupervisorId()));

        Estagiario estagiario = estagiarioRepository.findById(dto.getEstagiarioId())
                .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado com ID: " + dto.getEstagiarioId()));

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setTitulo(dto.getTitulo());
        avaliacao.setFeedbackPositivo(dto.getFeedbackPositivo());
        avaliacao.setPontosDeMelhoria(dto.getPontosDeMelhoria());
        avaliacao.setNotaFrequencia(dto.getNotaFrequencia());
        avaliacao.setNotaDesempenho(dto.getNotaDesempenho());
        avaliacao.setNotaOrganizacao(dto.getNotaOrganizacao());
        avaliacao.setNotaParticipacao(dto.getNotaParticipacao());
        avaliacao.setNotaComportamento(dto.getNotaComportamento());
        avaliacao.setDataAvaliacao(LocalDate.now());
        avaliacao.setSupervisor(supervisor);
        avaliacao.setEstagiario(estagiario);

        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);
        return toResponseDTO(avaliacaoSalva);
    }

    public List<AvaliacaoResponseDTO> listarPorEstagiario(Long estagiarioId) {
        if (!estagiarioRepository.existsById(estagiarioId)) {
            throw new EntityNotFoundException("Estagiário não encontrado com ID: " + estagiarioId);
        }
        return avaliacaoRepository.findByEstagiarioId(estagiarioId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private AvaliacaoResponseDTO toResponseDTO(Avaliacao avaliacao) {
        AvaliacaoResponseDTO dto = new AvaliacaoResponseDTO();
        dto.setId(avaliacao.getId());
        dto.setTitulo(avaliacao.getTitulo());
        dto.setFeedbackPositivo(avaliacao.getFeedbackPositivo());
        dto.setPontosDeMelhoria(avaliacao.getPontosDeMelhoria());
        dto.setNotaFrequencia(avaliacao.getNotaFrequencia());
        dto.setNotaDesempenho(avaliacao.getNotaDesempenho());
        dto.setNotaOrganizacao(avaliacao.getNotaOrganizacao());
        dto.setNotaParticipacao(avaliacao.getNotaParticipacao());
        dto.setNotaComportamento(avaliacao.getNotaComportamento());
        dto.setDataAvaliacao(avaliacao.getDataAvaliacao());

        dto.setSupervisor(supervisorService.toResponseDTO(avaliacao.getSupervisor()));
        dto.setEstagiario(estagiarioService.toResponseDTO(avaliacao.getEstagiario()));

        return dto;
    }
}