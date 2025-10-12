package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.EmpresaDTO;
import com.senai.skillmanager.dto.EmpresaResponseDTO;
import com.senai.skillmanager.model.Empresa;
import com.senai.skillmanager.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public EmpresaService(EmpresaRepository empresaRepository, PasswordEncoder passwordEncoder) {
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public EmpresaResponseDTO salvar(EmpresaDTO dto) {
        Empresa empresa = new Empresa();
        empresa.setNome(dto.getNome());
        empresa.setCnpj(dto.getCnpj());
        empresa.setSenha(passwordEncoder.encode(dto.getSenha()));

        Empresa empresaSalva = empresaRepository.save(empresa);
        return toResponseDTO(empresaSalva);
    }

    public List<EmpresaResponseDTO> listarTodos() {
        return empresaRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Empresa buscarEntidadePorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o ID: " + id));
    }

    public EmpresaResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidadePorId(id));
    }

    @Transactional
    public EmpresaResponseDTO atualizar(Long id, EmpresaDTO dto) {
        Empresa empresa = buscarEntidadePorId(id);
        empresa.setNome(dto.getNome());
        empresa.setCnpj(dto.getCnpj());
        Empresa empresaAtualizada = empresaRepository.save(empresa);
        return toResponseDTO(empresaAtualizada);
    }

    @Transactional
    public void excluir(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new EntityNotFoundException("Empresa não encontrada com o ID: " + id);
        }
        empresaRepository.deleteById(id);
    }

    public EmpresaResponseDTO toResponseDTO(Empresa empresa) {
        EmpresaResponseDTO dto = new EmpresaResponseDTO();
        dto.setId(empresa.getId());
        dto.setNome(empresa.getNome());
        dto.setCnpj(empresa.getCnpj());
        return dto;
    }
}