package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.*;
import com.senai.skillmanager.model.Endereco;
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
    private final PasswordEncoder passwordEncoder;
    private final FaculdadeService faculdadeService; // Para reutilizar a lógica

    public CoordenadorService(CoordenadorRepository coordenadorRepository,
                              FaculdadeRepository faculdadeRepository,
                              PasswordEncoder passwordEncoder,
                              @Lazy FaculdadeService faculdadeService) { // @Lazy
        this.coordenadorRepository = coordenadorRepository;
        this.faculdadeRepository = faculdadeRepository;
        this.passwordEncoder = passwordEncoder;
        this.faculdadeService = faculdadeService;
    }

    @Transactional
    public CoordenadorResponseDTO salvar(CoordenadorDTO dto) {
        coordenadorRepository.findByEmail(dto.getEmail()).ifPresent(c -> {
            throw new RuntimeException("Email já cadastrado.");
        });

        Faculdade faculdade;
        Optional<Faculdade> faculdadeOpt = faculdadeRepository.findByCnpj(dto.getFaculdadeCnpj());

        // --- LÓGICA DO FLUXO A e B (Get or Create) ---
        if (faculdadeOpt.isPresent()) {
            // FLUXO B: Faculdade já existe, apenas vincule
            faculdade = faculdadeOpt.get();
        } else {
            // FLUXO A: Faculdade não existe, crie-a

            // Validação: Para criar, os campos extras são obrigatórios
            if (dto.getFaculdadeNome() == null || dto.getFaculdadeTelefone() == null || dto.getFaculdadeEndereco() == null) {
                throw new IllegalArgumentException("Faculdade com CNPJ " + dto.getFaculdadeCnpj() + " não encontrada. " +
                        "Para cadastrá-la, forneça no mínimo 'faculdadeNome', 'faculdadeTelefone' e 'faculdadeEndereco'.");
            }

            // Reutiliza o FaculdadeService para salvar a nova faculdade
            FaculdadeDTO novaFaculdadeDTO = new FaculdadeDTO();
            novaFaculdadeDTO.setNome(dto.getFaculdadeNome());
            novaFaculdadeDTO.setCnpj(dto.getFaculdadeCnpj());
            novaFaculdadeDTO.setTelefone(dto.getFaculdadeTelefone());
            novaFaculdadeDTO.setSite(dto.getFaculdadeSite());
            novaFaculdadeDTO.setEndereco(dto.getFaculdadeEndereco());

            // O FaculdadeService.salvar() já contém a lógica de validação de CNPJ
            FaculdadeResponseDTO faculdadeSalva = faculdadeService.salvar(novaFaculdadeDTO);
            faculdade = faculdadeService.buscarEntidadePorId(faculdadeSalva.getId());
        }
        // --------------------------------------------

        Coordenador coordenador = new Coordenador();
        coordenador.setNome(dto.getNome());
        coordenador.setEmail(dto.getEmail());
        coordenador.setSenha(passwordEncoder.encode(dto.getSenha()));
        coordenador.setFaculdade(faculdade);

        Coordenador coordenadorSalvo = coordenadorRepository.save(coordenador);
        return toResponseDTO(coordenadorSalvo);
    }

    public List<CoordenadorResponseDTO> listarTodos() {
        return coordenadorRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CoordenadorResponseDTO buscarPorId(Long id) {
        Coordenador coordenador = coordenadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado com ID: " + id));
        return toResponseDTO(coordenador);
    }

    @Transactional
    public CoordenadorResponseDTO atualizar(Long id, CoordenadorDTO dto) {
        Coordenador coordenadorExistente = coordenadorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado com ID: " + id));

        // Lógica de atualização (simplificada para apenas buscar e vincular)
        Faculdade faculdade = faculdadeRepository.findByCnpj(dto.getFaculdadeCnpj())
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma faculdade encontrada com o CNPJ: " + dto.getFaculdadeCnpj()));

        coordenadorExistente.setNome(dto.getNome());
        coordenadorExistente.setEmail(dto.getEmail());
        coordenadorExistente.setFaculdade(faculdade);

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            coordenadorExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Coordenador coordenadorAtualizado = coordenadorRepository.save(coordenadorExistente);
        return toResponseDTO(coordenadorAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!coordenadorRepository.existsById(id)) {
            throw new EntityNotFoundException("Coordenador não encontrado com ID: " + id);
        }
        coordenadorRepository.deleteById(id);
    }

    // --- MÉTODOS HELPERS ---

    private CoordenadorResponseDTO toResponseDTO(Coordenador coordenador) {
        CoordenadorResponseDTO response = new CoordenadorResponseDTO();
        response.setId(coordenador.getId());
        response.setNome(coordenador.getNome());
        response.setEmail(coordenador.getEmail());
        response.setFaculdade(toFaculdadeResponseDTO(coordenador.getFaculdade()));
        return response;
    }

    private FaculdadeResponseDTO toFaculdadeResponseDTO(Faculdade faculdade) {
        FaculdadeResponseDTO response = new FaculdadeResponseDTO();
        response.setId(faculdade.getId());
        response.setNome(faculdade.getNome());
        response.setCnpj(faculdade.getCnpj());
        response.setTelefone(faculdade.getTelefone());
        response.setSite(faculdade.getSite());

        if (faculdade.getEndereco() != null) {
            response.setEndereco(toEnderecoDTO(faculdade.getEndereco()));
        }

        return response;
    }

    private EnderecoDTO toEnderecoDTO(Endereco endereco) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setLogradouro(endereco.getLogradouro());
        dto.setBairro(endereco.getBairro());
        dto.setCidade(endereco.getCidade());
        dto.setNumero(endereco.getNumero());
        dto.setEstados(endereco.getEstados());
        dto.setCep(endereco.getCep());
        return dto;
    }
}