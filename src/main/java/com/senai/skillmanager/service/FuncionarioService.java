package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.FuncionarioDTO;
import com.senai.skillmanager.dto.FuncionarioResponseDTO;
import com.senai.skillmanager.model.Empresa;
import com.senai.skillmanager.model.funcionario.Funcionario;
import com.senai.skillmanager.repository.EmpresaRepository;
import com.senai.skillmanager.repository.FuncionarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, EmpresaRepository empresaRepository, PasswordEncoder passwordEncoder) {
        this.funcionarioRepository = funcionarioRepository;
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public FuncionarioResponseDTO salvar(FuncionarioDTO dto) {
        funcionarioRepository.findByEmail(dto.getEmail()).ifPresent(f -> {
            throw new RuntimeException("Email já cadastrado.");
        });

        Empresa empresa = empresaRepository.findByCnpj(dto.getEmpresaCnpj())
                .orElseThrow(() -> new RuntimeException("Nenhuma empresa encontrada com o CNPJ: " + dto.getEmpresaCnpj()));

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setEmail(dto.getEmail());
        funcionario.setSenha(passwordEncoder.encode(dto.getSenha()));
        funcionario.setCargo(dto.getCargo());
        funcionario.setEmpresa(empresa);

        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
        return toResponseDTO(funcionarioSalvo);
    }

    public List<FuncionarioResponseDTO> listarTodos() {
        return funcionarioRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public FuncionarioResponseDTO buscarPorId(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
        return toResponseDTO(funcionario);
    }

    @Transactional
    public FuncionarioResponseDTO atualizar(Long id, FuncionarioDTO dto) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));

        Empresa empresa = empresaRepository.findByCnpj(dto.getEmpresaCnpj())
                .orElseThrow(() -> new RuntimeException("Nenhuma empresa encontrada com o CNPJ: " + dto.getEmpresaCnpj()));

        funcionarioExistente.setNome(dto.getNome());
        funcionarioExistente.setEmail(dto.getEmail());
        funcionarioExistente.setCargo(dto.getCargo());
        funcionarioExistente.setEmpresa(empresa);

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            funcionarioExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionarioExistente);
        return toResponseDTO(funcionarioAtualizado);
    }

    public void excluir(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new RuntimeException("Funcionário não encontrado com ID: " + id);
        }
        funcionarioRepository.deleteById(id);
    }

    private FuncionarioResponseDTO toResponseDTO(Funcionario funcionario) {
        FuncionarioResponseDTO response = new FuncionarioResponseDTO();
        response.setId(funcionario.getId());
        response.setNome(funcionario.getNome());
        response.setEmail(funcionario.getEmail());
        response.setCargo(funcionario.getCargo());
        response.setEmpresa(funcionario.getEmpresa());
        return response;
    }
}