package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.EmpresaDTO;
import com.senai.skillmanager.dto.EmpresaResponseDTO;
import com.senai.skillmanager.dto.EnderecoDTO;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.empresa.Empresa;
import com.senai.skillmanager.model.empresa.TipoEmpresa; // Importa o novo Enum
import com.senai.skillmanager.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public EmpresaResponseDTO salvar(EmpresaDTO dto) {
        empresaRepository.findByCnpj(dto.getCnpj()).ifPresent(e -> {
            throw new RuntimeException("Empresa já cadastrada com este CNPJ.");
        });

        // --- SUA NOVA LÓGICA DE VALIDAÇÃO ---
        validarInscricoes(dto.getTipoEmpresa(), dto.getInscricaoEstadual(), dto.getInscricaoMunicipal());
        // -------------------------------------

        Empresa empresa = new Empresa();
        empresa.setNome(dto.getNome());
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setCnpj(dto.getCnpj());
        empresa.setTipoEmpresa(dto.getTipoEmpresa()); // <-- Salvar o novo campo
        empresa.setInscricaoEstadual(dto.getInscricaoEstadual());
        empresa.setInscricaoMunicipal(dto.getInscricaoMunicipal());
        empresa.setEndereco(toEnderecoEntity(dto.getEndereco()));

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

    public Empresa buscarEntidadePorCnpj(String cnpj) {
        return empresaRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com o CNPJ: " + cnpj));
    }

    public EmpresaResponseDTO buscarPorId(Long id) {
        return toResponseDTO(buscarEntidadePorId(id));
    }

    @Transactional
    public EmpresaResponseDTO atualizar(Long id, EmpresaDTO dto) {
        Empresa empresa = buscarEntidadePorId(id);

        // --- SUA NOVA LÓGICA DE VALIDAÇÃO ---
        validarInscricoes(dto.getTipoEmpresa(), dto.getInscricaoEstadual(), dto.getInscricaoMunicipal());
        // -------------------------------------

        empresa.setNome(dto.getNome());
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setCnpj(dto.getCnpj());
        empresa.setTipoEmpresa(dto.getTipoEmpresa());
        empresa.setInscricaoEstadual(dto.getInscricaoEstadual());
        empresa.setInscricaoMunicipal(dto.getInscricaoMunicipal());

        Endereco endereco = empresa.getEndereco() != null ? empresa.getEndereco() : new Endereco();
        empresa.setEndereco(toEnderecoEntity(dto.getEndereco(), endereco));

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

    // --- NOVO MÉTODO DE VALIDAÇÃO PRIVADO ---
    private void validarInscricoes(TipoEmpresa tipo, String ie, String im) {
        if (tipo == TipoEmpresa.COMERCIO) {
            if (ie == null || ie.isBlank()) {
                throw new IllegalArgumentException("Empresas de Comércio devem possuir Inscrição Estadual.");
            }
        } else if (tipo == TipoEmpresa.SERVICO) {
            if (im == null || im.isBlank()) {
                throw new IllegalArgumentException("Empresas de Serviço devem possuir Inscrição Municipal.");
            }
        } else if (tipo == TipoEmpresa.COMERCIO_E_SERVICO) {
            if ((ie == null || ie.isBlank()) || (im == null || im.isBlank())) {
                throw new IllegalArgumentException("Empresas de Comércio e Serviço devem possuir ambas as Inscrições (Estadual e Municipal).");
            }
        }
    }
    // ------------------------------------------

    // --- MÉTODOS HELPERS ATUALIZADOS ---
    public EmpresaResponseDTO toResponseDTO(Empresa empresa) {
        EmpresaResponseDTO dto = new EmpresaResponseDTO();
        dto.setId(empresa.getId());
        dto.setNome(empresa.getNome());
        dto.setRazaoSocial(empresa.getRazaoSocial());
        dto.setCnpj(empresa.getCnpj());
        dto.setTipoEmpresa(empresa.getTipoEmpresa());
        dto.setInscricaoEstadual(empresa.getInscricaoEstadual());
        dto.setInscricaoMunicipal(empresa.getInscricaoMunicipal());
        if (empresa.getEndereco() != null) {
            dto.setEndereco(toEnderecoDTO(empresa.getEndereco()));
        }
        return dto;
    }

    private Endereco toEnderecoEntity(EnderecoDTO dto) {
        return toEnderecoEntity(dto, new Endereco());
    }

    private Endereco toEnderecoEntity(EnderecoDTO dto, Endereco endereco) {
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setBairro(dto.getBairro());
        endereco.setCidade(dto.getCidade());
        endereco.setNumero(dto.getNumero());
        endereco.setEstados(dto.getEstados());
        endereco.setCep(dto.getCep());
        return endereco;
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