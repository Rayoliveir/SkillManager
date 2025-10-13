package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.DadosEstagioDTO;
import com.senai.skillmanager.dto.DadosEstagioResponseDTO;
import com.senai.skillmanager.model.estagiario.DadosEstagio;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.funcionario.Funcionario;
import com.senai.skillmanager.repository.DadosEstagioRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.FuncionarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DadosEstagioService {

    private final DadosEstagioRepository dadosEstagioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final EstagiarioRepository estagiarioRepository;

    public DadosEstagioService(DadosEstagioRepository dadosEstagioRepository, FuncionarioRepository funcionarioRepository, EstagiarioRepository estagiarioRepository) {
        this.dadosEstagioRepository = dadosEstagioRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.estagiarioRepository = estagiarioRepository;
    }

    @Transactional
    public DadosEstagioResponseDTO salvar(DadosEstagioDTO dto) {
        Funcionario supervisor = funcionarioRepository.findById(dto.getSupervisorId())
                .orElseThrow(() -> new RuntimeException("Funcionário supervisor não encontrado com ID: " + dto.getSupervisorId()));

        Estagiario estagiario = estagiarioRepository.findById(dto.getEstagiarioId())
                .orElseThrow(() -> new RuntimeException("Estagiário não encontrado com ID: " + dto.getEstagiarioId()));

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
                .orElseThrow(() -> new RuntimeException("Dados de estágio não encontrados com ID: " + id));
        return toResponseDTO(dadosEstagio);
    }

    @Transactional
    public DadosEstagioResponseDTO atualizar(Long id, DadosEstagioDTO dto) {
        DadosEstagio dadosEstagioExistente = dadosEstagioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dados de estágio não encontrados com ID: " + id));

        Funcionario supervisor = funcionarioRepository.findById(dto.getSupervisorId())
                .orElseThrow(() -> new RuntimeException("Funcionário supervisor não encontrado com ID: " + dto.getSupervisorId()));

        Estagiario estagiario = estagiarioRepository.findById(dto.getEstagiarioId())
                .orElseThrow(() -> new RuntimeException("Estagiário não encontrado com ID: " + dto.getEstagiarioId()));

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
            throw new RuntimeException("Dados de estágio não encontrados com ID: " + id);
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
        dto.setSupervisor(dadosEstagio.getSupervisor());
        dto.setEstagiario(dadosEstagio.getEstagiario());
        return dto;
    }
}