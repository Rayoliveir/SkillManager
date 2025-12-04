package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.EnderecoDTO;
import com.senai.skillmanager.model.Endereco;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    public Endereco toEntity(EnderecoDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setNumero(dto.getNumero());
        endereco.setEstados(dto.getEstados());
        endereco.setCep(dto.getCep());
        return endereco;
    }

    public EnderecoDTO toResponseDTO(Endereco entity) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setLogradouro(entity.getLogradouro());
        dto.setBairro(entity.getBairro());
        dto.setCidade(entity.getCidade());
        dto.setNumero(entity.getNumero());
        dto.setEstados(entity.getEstados());
        dto.setCep(entity.getCep());
        return dto;
    }
}