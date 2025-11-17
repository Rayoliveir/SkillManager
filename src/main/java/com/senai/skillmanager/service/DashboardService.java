package com.senai.skillmanager.service;

import com.senai.skillmanager.dto.AvaliacaoResponseDTO;
import com.senai.skillmanager.dto.DashboardEstagiarioDTO;
import com.senai.skillmanager.dto.DashboardResponseDTO;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final EstagiarioRepository estagiarioRepository;
    private final SupervisorRepository supervisorRepository;
    private final CoordenadorRepository coordenadorRepository;
    private final AvaliacaoService avaliacaoService;
    private final EstagiarioService estagiarioService; // Adicionado

    public DashboardService(EstagiarioRepository estagiarioRepository,
                            SupervisorRepository supervisorRepository,
                            CoordenadorRepository coordenadorRepository,
                            AvaliacaoService avaliacaoService,
                            EstagiarioService estagiarioService) { // Adicionado
        this.estagiarioRepository = estagiarioRepository;
        this.supervisorRepository = supervisorRepository;
        this.coordenadorRepository = coordenadorRepository;
        this.avaliacaoService = avaliacaoService;
        this.estagiarioService = estagiarioService; // Adicionado
    }

    public DashboardResponseDTO getDashboardData(Authentication authentication) {
        String email = authentication.getName();
        DashboardResponseDTO response = new DashboardResponseDTO();

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"))) {
            Estagiario estagiario = estagiarioRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Estagiário não encontrado."));

            response.setNomeUsuario(estagiario.getNome());
            response.setDescricaoPrincipal("Meu Progresso");

            // Buscar dados para o DTO do estagiário
            List<AvaliacaoResponseDTO> avaliacoes = avaliacaoService.listarPorEstagiario(estagiario.getId(), authentication);

            // Criar e preencher o seu DTO
            DashboardEstagiarioDTO estagiarioDTO = new DashboardEstagiarioDTO();
            estagiarioDTO.setDadosEstagiario(estagiarioService.toResponseDTO(estagiario));
            estagiarioDTO.setAvaliacoes(avaliacoes);

            // Adicionar o seu DTO à resposta principal
            response.setDashboardEstagiario(estagiarioDTO);

        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUPERVISOR")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_GERENTE"))) {

            Supervisor supervisor = supervisorRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Supervisor não encontrado."));

            response.setNomeUsuario(supervisor.getNome());
            response.setDescricaoPrincipal("Estagiários da Empresa: " + supervisor.getEmpresa().getNome());

            List<Estagiario> estagiariosDaEmpresa = estagiarioRepository.findByEmpresaId(supervisor.getEmpresa().getId());
            response.setTotalEstagiarios(estagiariosDaEmpresa.size());

            List<String> nomesEstagiarios = estagiariosDaEmpresa.stream()
                    .map(Estagiario::getNome)
                    .collect(Collectors.toList());
            response.setListaNomesEstagiarios(nomesEstagiarios);

        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_FACULDADE"))) {

            Coordenador coordenador = coordenadorRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Coordenador não encontrado."));

            response.setNomeUsuario(coordenador.getNome());
            response.setDescricaoPrincipal("Estagiários da Faculdade: " + coordenador.getFaculdade().getNome());

            List<Estagiario> estagiariosDaFaculdade = estagiarioRepository.findByDadosAcademicos_Faculdade_Id(coordenador.getFaculdade().getId());
            response.setTotalEstagiarios(estagiariosDaFaculdade.size());

            List<String> nomesEstagiarios = estagiariosDaFaculdade.stream()
                    .map(Estagiario::getNome)
                    .collect(Collectors.toList());
            response.setListaNomesEstagiarios(nomesEstagiarios);
        } else {
            response.setNomeUsuario("Administrador");
            response.setDescricaoPrincipal("Visão Geral do Sistema");
        }

        return response;
    }
}