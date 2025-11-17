package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.DadosAcademicosDTO;
import com.senai.skillmanager.dto.DadosAcademicosResponseDTO;
import com.senai.skillmanager.dto.EnderecoDTO;
import com.senai.skillmanager.dto.FaculdadeResponseDTO;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.estagiario.DadosAcademicos;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.DadosAcademicosRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.FaculdadeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DadosAcademicosService {

    private final DadosAcademicosRepository dadosAcademicosRepository;
    private final FaculdadeRepository faculdadeRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final CoordenadorRepository coordenadorRepository;

    public DadosAcademicosService(DadosAcademicosRepository dadosAcademicosRepository,
                                  FaculdadeRepository faculdadeRepository,
                                  EstagiarioRepository estagiarioRepository,
                                  CoordenadorRepository coordenadorRepository) {
        this.dadosAcademicosRepository = dadosAcademicosRepository;
        this.faculdadeRepository = faculdadeRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.coordenadorRepository = coordenadorRepository;
    }

    @Transactional
    public DadosAcademicosResponseDTO salvar(DadosAcademicosDTO dto) {
        Faculdade faculdade = faculdadeRepository.findByCnpj(dto.getFaculdadeCnpj())
                .orElseThrow(() -> new EntityNotFoundException("Faculdade não encontrada com CNPJ: " + dto.getFaculdadeCnpj()));

        DadosAcademicos dadosAcademicos = new DadosAcademicos();
        dadosAcademicos.setFaculdade(faculdade);
        dadosAcademicos.setCurso(dto.getCurso());
        dadosAcademicos.setPeriodoSemestre(dto.getPeriodoSemestre());
        dadosAcademicos.setPrevisaoFormatura(YearMonth.parse(dto.getPrevisaoFormatura()));
        dadosAcademicos.setRa(dto.getRa());

        DadosAcademicos dadosSalvos = dadosAcademicosRepository.save(dadosAcademicos);
        return toResponseDTO(dadosSalvos);
    }

    public List<DadosAcademicosResponseDTO> listarTodos() {
        return dadosAcademicosRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DadosAcademicosResponseDTO buscarPorId(Long id, Authentication authentication) {
        DadosAcademicos dadosAcademicos = buscarEntidadePorId(id);
        checkOwnership(dadosAcademicos, authentication);
        return toResponseDTO(dadosAcademicos);
    }

    @Transactional
    public DadosAcademicosResponseDTO atualizar(Long id, DadosAcademicosDTO dto, Authentication authentication) {
        DadosAcademicos dadosAcademicos = buscarEntidadePorId(id);
        checkOwnership(dadosAcademicos, authentication);

        Faculdade faculdade = faculdadeRepository.findByCnpj(dto.getFaculdadeCnpj())
                .orElseThrow(() -> new EntityNotFoundException("Faculdade não encontrada com CNPJ: " + dto.getFaculdadeCnpj()));

        dadosAcademicos.setFaculdade(faculdade);
        dadosAcademicos.setCurso(dto.getCurso());
        dadosAcademicos.setPeriodoSemestre(dto.getPeriodoSemestre());
        dadosAcademicos.setPrevisaoFormatura(YearMonth.parse(dto.getPrevisaoFormatura()));
        dadosAcademicos.setRa(dto.getRa());

        DadosAcademicos dadosAtualizados = dadosAcademicosRepository.save(dadosAcademicos);
        return toResponseDTO(dadosAtualizados);
    }

    @Transactional
    public void excluir(Long id) {
        if (!dadosAcademicosRepository.existsById(id)) {
            throw new EntityNotFoundException("Dados Acadêmicos não encontrados com ID: " + id);
        }
        dadosAcademicosRepository.deleteById(id);
    }

    private DadosAcademicos buscarEntidadePorId(Long id) {
        return dadosAcademicosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dados Acadêmicos não encontrados com ID: " + id));
    }

    private void checkOwnership(DadosAcademicos dadosAcademicos, Authentication authentication) {
        String authEmail = authentication.getName();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return;
        }

        Estagiario estagiario = estagiarioRepository.findByDadosAcademicos_Id(dadosAcademicos.getId())
                .orElseThrow(() -> new EntityNotFoundException("Nenhum estagiário associado a estes dados acadêmicos."));

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"))) {
            if (estagiario.getEmail().equals(authEmail)) {
                return;
            }
        }

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_FACULDADE"))) {
            Coordenador coordenador = coordenadorRepository.findByEmail(authEmail)
                    .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado."));

            if (coordenador.getFaculdade().getId().equals(dadosAcademicos.getFaculdade().getId())) {
                return;
            }
        }

        throw new SecurityException("Acesso negado. Você não tem permissão para acessar este recurso.");
    }

    private DadosAcademicosResponseDTO toResponseDTO(DadosAcademicos dados) {
        if (dados == null) return null;

        DadosAcademicosResponseDTO dto = new DadosAcademicosResponseDTO();
        dto.setId(dados.getId());
        dto.setFaculdade(toFaculdadeResponseDTO(dados.getFaculdade()));
        dto.setCurso(dados.getCurso());
        dto.setPeriodoSemestre(dados.getPeriodoSemestre());
        dto.setPrevisaoFormatura(dados.getPrevisaoFormatura().toString());
        dto.setRa(dados.getRa());
        return dto;
    }

    private FaculdadeResponseDTO toFaculdadeResponseDTO(Faculdade faculdade) {
        if (faculdade == null) return null;

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
        if (endereco == null) return null;

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