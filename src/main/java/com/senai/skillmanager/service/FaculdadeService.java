package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.EnderecoDTO;
import com.senai.skillmanager.dto.FaculdadeDTO;
import com.senai.skillmanager.dto.FaculdadeResponseDTO;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.repository.FaculdadeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaculdadeService {

    private final FaculdadeRepository faculdadeRepository;
    private final PasswordEncoder passwordEncoder;

    public FaculdadeService(FaculdadeRepository faculdadeRepository, PasswordEncoder passwordEncoder) {
        this.faculdadeRepository = faculdadeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public FaculdadeResponseDTO salvar(FaculdadeDTO dto) {
        faculdadeRepository.findByCnpj(dto.getCnpj()).ifPresent(f -> {
            throw new RuntimeException("CNPJ já cadastrado.");
        });
        faculdadeRepository.findByEmail(dto.getEmail()).ifPresent(f -> {
            throw new RuntimeException("Email já cadastrado.");
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
        faculdade.setEmail(dto.getEmail());
        faculdade.setSite(dto.getSite());
        faculdade.setEndereco(endereco);

        // ✨ A CORREÇÃO ESSENCIAL ESTÁ AQUI ✨
        // Criptografa a senha antes de salvar no banco de dados
        faculdade.setSenha(passwordEncoder.encode(dto.getSenha()));

        Faculdade faculdadeSalva = faculdadeRepository.save(faculdade);
        return toResponseDTO(faculdadeSalva);
    }

    public List<FaculdadeResponseDTO> listarTodos() {
        return faculdadeRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public FaculdadeResponseDTO buscarPorId(Long id) {
        Faculdade faculdade = faculdadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculdade não encontrada com ID: " + id));
        return toResponseDTO(faculdade);
    }

    @Transactional
    public FaculdadeResponseDTO atualizar(Long id, FaculdadeDTO dto) {
        Faculdade faculdadeExistente = faculdadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculdade não encontrada com ID: " + id));

        faculdadeExistente.setNome(dto.getNome());
        faculdadeExistente.setCnpj(dto.getCnpj());
        faculdadeExistente.setTelefone(dto.getTelefone());
        faculdadeExistente.setEmail(dto.getEmail());
        faculdadeExistente.setSite(dto.getSite());

        EnderecoDTO enderecoDTO = dto.getEndereco();
        Endereco endereco = faculdadeExistente.getEndereco();
        endereco.setLogradouro(enderecoDTO.getLogradouro());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setEstados(enderecoDTO.getEstados());
        endereco.setCep(enderecoDTO.getCep());

        // Garante que a senha seja criptografada também na atualização, se for alterada
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            faculdadeExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Faculdade faculdadeAtualizada = faculdadeRepository.save(faculdadeExistente);
        return toResponseDTO(faculdadeAtualizada);
    }

    public void excluir(Long id) {
        if (!faculdadeRepository.existsById(id)) {
            throw new RuntimeException("Faculdade não encontrada com ID: " + id);
        }
        faculdadeRepository.deleteById(id);
    }

    private FaculdadeResponseDTO toResponseDTO(Faculdade faculdade) {
        FaculdadeResponseDTO response = new FaculdadeResponseDTO();
        response.setId(faculdade.getId());
        response.setNome(faculdade.getNome());
        response.setCnpj(faculdade.getCnpj());
        response.setTelefone(faculdade.getTelefone());
        response.setEmail(faculdade.getEmail());
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