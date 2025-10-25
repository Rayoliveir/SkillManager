package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.EnderecoDTO;
import com.senai.skillmanager.dto.FaculdadeDTO;
import com.senai.skillmanager.dto.FaculdadeResponseDTO;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.repository.FaculdadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaculdadeService {

    private final FaculdadeRepository faculdadeRepository;

    public FaculdadeService(FaculdadeRepository faculdadeRepository) {
        this.faculdadeRepository = faculdadeRepository;
    }

    @Transactional
    public FaculdadeResponseDTO salvar(FaculdadeDTO dto) {
        faculdadeRepository.findByCnpj(dto.getCnpj()).ifPresent(f -> {
            throw new RuntimeException("CNPJ já cadastrado.");
        });

        EnderecoDTO enderecoDTO = dto.getEndereco();
        Endereco endereco = new Endereco();
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setEstados(enderecoDTO.getEstados());
        endereco.setCep(enderecoDTO.getCep());

        Faculdade faculdade = new Faculdade();
        faculdade.setNome(dto.getNome());
        faculdade.setCnpj(dto.getCnpj());
        faculdade.setTelefone(dto.getTelefone());
        faculdade.setSite(dto.getSite());
        faculdade.setEndereco(endereco);

        Faculdade faculdadeSalva = faculdadeRepository.save(faculdade);
        return toResponseDTO(faculdadeSalva);
    }

    public List<FaculdadeResponseDTO> listarTodos() {
        return faculdadeRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Faculdade buscarEntidadePorId(Long id) {
        return faculdadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Faculdade não encontrada com ID: " + id));
    }

    public Faculdade buscarEntidadePorCnpj(String cnpj) {
        return faculdadeRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("Faculdade não encontrada com o CNPJ: " + cnpj));
    }

    public FaculdadeResponseDTO buscarPorId(Long id) {
        Faculdade faculdade = buscarEntidadePorId(id);
        return toResponseDTO(faculdade);
    }

    @Transactional
    public FaculdadeResponseDTO atualizar(Long id, FaculdadeDTO dto) {
        Faculdade faculdadeExistente = buscarEntidadePorId(id);

        faculdadeExistente.setNome(dto.getNome());
        faculdadeExistente.setCnpj(dto.getCnpj());
        faculdadeExistente.setTelefone(dto.getTelefone());
        faculdadeExistente.setSite(dto.getSite());

        EnderecoDTO enderecoDTO = dto.getEndereco();
        Endereco endereco = faculdadeExistente.getEndereco();
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setEstados(enderecoDTO.getEstados());
        endereco.setCep(enderecoDTO.getCep());

        Faculdade faculdadeAtualizada = faculdadeRepository.save(faculdadeExistente);
        return toResponseDTO(faculdadeAtualizada);
    }

    @Transactional
    public void excluir(Long id) {
        if (!faculdadeRepository.existsById(id)) {
            throw new EntityNotFoundException("Faculdade não encontrada com ID: " + id);
        }
        faculdadeRepository.deleteById(id);
    }

    private FaculdadeResponseDTO toResponseDTO(Faculdade faculdade) {
        FaculdadeResponseDTO response = new FaculdadeResponseDTO();
        response.setId(faculdade.getId());
        response.setNome(faculdade.getNome());
        response.setCnpj(faculdade.getCnpj());
        response.setTelefone(faculdade.getTelefone());
        response.setSite(faculdade.getSite());

        if (faculdade.getEndereco() != null) {
            EnderecoDTO enderecoDTO = new EnderecoDTO();
            enderecoDTO.setLogradouro(faculdade.getEndereco().getLogradouro());
            enderecoDTO.setBairro(faculdade.getEndereco().getBairro());
            enderecoDTO.setCidade(faculdade.getEndereco().getCidade());
            enderecoDTO.setNumero(faculdade.getEndereco().getNumero());
            enderecoDTO.setEstados(faculdade.getEndereco().getEstados());
            enderecoDTO.setCep(faculdade.getEndereco().getCep());
            response.setEndereco(enderecoDTO);
        }

        return response;
    }
}