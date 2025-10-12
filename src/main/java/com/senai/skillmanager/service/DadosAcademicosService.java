package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.DadosAcademicosDTO;
import com.senai.skillmanager.dto.DadosAcademicosResponseDTO;
import com.senai.skillmanager.model.estagiario.DadosAcademicos;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.repository.DadosAcademicosRepository;
import com.senai.skillmanager.repository.FaculdadeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DadosAcademicosService {

    private final DadosAcademicosRepository dadosAcademicosRepository;
    private final FaculdadeRepository faculdadeRepository;

    public DadosAcademicosService(DadosAcademicosRepository dadosAcademicosRepository, FaculdadeRepository faculdadeRepository) {
        this.dadosAcademicosRepository = dadosAcademicosRepository;
        this.faculdadeRepository = faculdadeRepository;
    }

    @Transactional
    public DadosAcademicosResponseDTO salvar(DadosAcademicosDTO dto) {
        Faculdade faculdade = faculdadeRepository.findById(dto.getFaculdadeId())
                .orElseThrow(() -> new RuntimeException("Faculdade não encontrada com ID: " + dto.getFaculdadeId()));

        DadosAcademicos dadosAcademicos = new DadosAcademicos();
        dadosAcademicos.setCurso(dto.getCurso());
        dadosAcademicos.setPeriodoSemestre(dto.getPeriodoSemestre());
        dadosAcademicos.setPrevisaoFormatura(dto.getPrevisaoFormatura());
        dadosAcademicos.setRa(dto.getRa());
        dadosAcademicos.setFaculdade(faculdade);

        DadosAcademicos dadosSalvos = dadosAcademicosRepository.save(dadosAcademicos);
        return toResponseDTO(dadosSalvos);
    }

    public List<DadosAcademicosResponseDTO> listarTodos() {
        return dadosAcademicosRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DadosAcademicosResponseDTO buscarPorId(Long id) {
        DadosAcademicos dados = dadosAcademicosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dados Acadêmicos não encontrados com ID: " + id));
        return toResponseDTO(dados);
    }

    @Transactional
    public DadosAcademicosResponseDTO atualizar(Long id, DadosAcademicosDTO dto) {
        DadosAcademicos dadosExistentes = dadosAcademicosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dados Acadêmicos não encontrados com ID: " + id));

        Faculdade faculdade = faculdadeRepository.findById(dto.getFaculdadeId())
                .orElseThrow(() -> new RuntimeException("Faculdade não encontrada com ID: " + dto.getFaculdadeId()));

        dadosExistentes.setCurso(dto.getCurso());
        dadosExistentes.setPeriodoSemestre(dto.getPeriodoSemestre());
        dadosExistentes.setPrevisaoFormatura(dto.getPrevisaoFormatura());
        dadosExistentes.setRa(dto.getRa());
        dadosExistentes.setFaculdade(faculdade);

        DadosAcademicos dadosAtualizados = dadosAcademicosRepository.save(dadosExistentes);
        return toResponseDTO(dadosAtualizados);
    }

    public void excluir(Long id) {
        if (!dadosAcademicosRepository.existsById(id)) {
            throw new RuntimeException("Dados Acadêmicos não encontrados com ID: " + id);
        }
        dadosAcademicosRepository.deleteById(id);
    }

    private DadosAcademicosResponseDTO toResponseDTO(DadosAcademicos dados) {
        if (dados == null) return null;

        DadosAcademicosResponseDTO dto = new DadosAcademicosResponseDTO();
        dto.setId(dados.getId());
        dto.setCurso(dados.getCurso());
        dto.setPeriodoSemestre(dados.getPeriodoSemestre());
        dto.setPrevisaoFormatura(dados.getPrevisaoFormatura());
        dto.setRa(dados.getRa());
        dto.setFaculdade(dados.getFaculdade());

        return dto;
    }
}