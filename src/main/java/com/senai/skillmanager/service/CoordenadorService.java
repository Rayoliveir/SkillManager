package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.CoordenadorDTO;
import com.senai.skillmanager.dto.CoordenadorResponseDTO;
import com.senai.skillmanager.dto.FaculdadeResponseDTO;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.FaculdadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoordenadorService {

    private final CoordenadorRepository coordenadorRepository;
    private final FaculdadeRepository faculdadeRepository;
    private final FaculdadeService faculdadeService;
    private final PasswordEncoder passwordEncoder;

    public CoordenadorService(CoordenadorRepository coordenadorRepository,
                              FaculdadeRepository faculdadeRepository,
                              @Lazy FaculdadeService faculdadeService,
                              PasswordEncoder passwordEncoder) {
        this.coordenadorRepository = coordenadorRepository;
        this.faculdadeRepository = faculdadeRepository;
        this.faculdadeService = faculdadeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CoordenadorResponseDTO salvar(CoordenadorDTO dto) {
        if (dto.getSenha() == null || dto.getSenha().isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória para o cadastro.");
        }

        if (coordenadorRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um coordenador cadastrado com este e-mail.");
        }

        Coordenador coordenador = new Coordenador();
        coordenador.setNome(dto.getNome());
        coordenador.setEmail(dto.getEmail());
        coordenador.setSenha(passwordEncoder.encode(dto.getSenha()));

        Optional<Faculdade> faculdadeExistente = faculdadeRepository.findByCnpj(dto.getFaculdadeCnpj());

        if (faculdadeExistente.isPresent()) {
            coordenador.setFaculdade(faculdadeExistente.get());
        } else {
            // Cria nova faculdade
            com.senai.skillmanager.dto.FaculdadeDTO faculdadeDTO = new com.senai.skillmanager.dto.FaculdadeDTO();
            faculdadeDTO.setNome(dto.getFaculdadeNome());
            faculdadeDTO.setCnpj(dto.getFaculdadeCnpj());
            faculdadeDTO.setTelefone(dto.getFaculdadeTelefone());
            faculdadeDTO.setSite(dto.getFaculdadeSite());
            faculdadeDTO.setEndereco(dto.getFaculdadeEndereco());

            faculdadeService.salvar(faculdadeDTO);
            coordenador.setFaculdade(faculdadeRepository.findByCnpj(dto.getFaculdadeCnpj()).orElseThrow());
        }

        Coordenador salvo = coordenadorRepository.save(coordenador);
        return toResponseDTO(salvo);
    }

    // --- MÉTODOS QUE FALTAVAM ---
    public List<CoordenadorResponseDTO> listarTodos() {
        return coordenadorRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CoordenadorResponseDTO buscarPorId(Long id) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado."));
        return toResponseDTO(coordenador);
    }

    @Transactional
    public void excluir(Long id) {
        if (!coordenadorRepository.existsById(id)) {
            throw new EntityNotFoundException("Coordenador não encontrado.");
        }
        coordenadorRepository.deleteById(id);
    }
    // ----------------------------

    @Transactional
    public CoordenadorResponseDTO atualizar(Long id, CoordenadorDTO dto) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado."));

        coordenador.setNome(dto.getNome());
        coordenador.setEmail(dto.getEmail());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            coordenador.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Coordenador atualizado = coordenadorRepository.save(coordenador);
        return toResponseDTO(atualizado);
    }

    public CoordenadorResponseDTO toResponseDTO(Coordenador entity) {
        if (entity == null) return null;
        CoordenadorResponseDTO dto = new CoordenadorResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        if (entity.getFaculdade() != null) {
            dto.setFaculdade(toFaculdadeResponseDTO(entity.getFaculdade()));
        }
        return dto;
    }

    public FaculdadeResponseDTO toFaculdadeResponseDTO(Faculdade entity) {
        if (entity == null) return null;
        FaculdadeResponseDTO dto = new FaculdadeResponseDTO();
        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        dto.setCnpj(entity.getCnpj());
        dto.setTelefone(entity.getTelefone());
        dto.setSite(entity.getSite());
        return dto;
    }
}