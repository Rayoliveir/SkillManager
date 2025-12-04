package com.senai.skillmanager.controller;

import com.senai.skillmanager.dto.EstagiarioResponseDTO;
import com.senai.skillmanager.model.avaliacao.Avaliacao;
import com.senai.skillmanager.model.competencia.Competencia;
import com.senai.skillmanager.model.estagiario.DadosEstagio;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.repository.*;
import com.senai.skillmanager.service.CoordenadorService;
import com.senai.skillmanager.service.EstagiarioService;
import com.senai.skillmanager.service.SupervisorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final EstagiarioRepository estagiarioRepository;
    private final EstagiarioService estagiarioService;
    private final SupervisorRepository supervisorRepository;
    private final SupervisorService supervisorService;
    private final CoordenadorRepository coordenadorRepository;
    private final CoordenadorService coordenadorService;
    private final AvaliacaoRepository avaliacaoRepository;
    private final CompetenciaRepository competenciaRepository;
    private final EmpresaRepository empresaRepository;
    private final DadosEstagioRepository dadosEstagioRepository;

    public DashboardController(EstagiarioRepository estagiarioRepository,
                               EstagiarioService estagiarioService,
                               SupervisorRepository supervisorRepository,
                               SupervisorService supervisorService,
                               CoordenadorRepository coordenadorRepository,
                               CoordenadorService coordenadorService,
                               AvaliacaoRepository avaliacaoRepository,
                               CompetenciaRepository competenciaRepository,
                               EmpresaRepository empresaRepository,
                               DadosEstagioRepository dadosEstagioRepository) {
        this.estagiarioRepository = estagiarioRepository;
        this.estagiarioService = estagiarioService;
        this.supervisorRepository = supervisorRepository;
        this.supervisorService = supervisorService;
        this.coordenadorRepository = coordenadorRepository;
        this.coordenadorService = coordenadorService;
        this.avaliacaoRepository = avaliacaoRepository;
        this.competenciaRepository = competenciaRepository;
        this.empresaRepository = empresaRepository;
        this.dadosEstagioRepository = dadosEstagioRepository;
    }

    @GetMapping
    public ResponseEntity<?> getDashboardData(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        String email = authentication.getName().toLowerCase();

        try {
            // --- 1. ESTAGIÁRIO ---
            Optional<Estagiario> estagiario = estagiarioRepository.findByEmail(email);
            if (estagiario.isEmpty()) estagiario = estagiarioRepository.findByEmail(authentication.getName());

            if (estagiario.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("role", "ESTAGIARIO");

                Map<String, Object> dashEstagiario = new HashMap<>();
                EstagiarioResponseDTO dto = estagiarioService.toResponseDTO(estagiario.get());

                dashEstagiario.put("dadosEstagiario", dto);
                dashEstagiario.put("avaliacoes", dto.getAvaliacoes() != null ? dto.getAvaliacoes() : Collections.emptyList());

                // --- CORREÇÃO DO LOOP INFINITO & NOME DA SKILL ---
                // Transforma a lista de entidades em uma lista de Mapas simples
                List<Competencia> competenciasEntidade = competenciaRepository.findByEstagiarioId(estagiario.get().getId());

                List<Map<String, Object>> competenciasSafe = competenciasEntidade.stream().map(c -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", c.getId());
                    // MUDANÇA: Se c.getDescricao() não existe, usamos c.getNome()
                    map.put("descricao", c.getNome());
                    map.put("nivel", c.getNivel());
                    return map;
                }).collect(Collectors.toList());

                dashEstagiario.put("competencias", competenciasSafe);
                // ------------------------------------------------

                Optional<DadosEstagio> dadosEstagio = dadosEstagioRepository.findByEstagiarioId(estagiario.get().getId());

                // Gera as conquistas baseado nas listas seguras
                List<Map<String, String>> conquistas = gerarConquistas(competenciasEntidade, dto.getAvaliacoes(), dadosEstagio.isPresent());
                dashEstagiario.put("conquistas", conquistas);

                response.put("dashboardEstagiario", dashEstagiario);
                return ResponseEntity.ok(response);
            }

            // --- 2. SUPERVISOR ---
            Optional<Supervisor> supervisor = supervisorRepository.findByEmail(email);
            if (supervisor.isEmpty()) supervisor = supervisorRepository.findByEmail(authentication.getName());

            if (supervisor.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("role", "SUPERVISOR");

                Map<String, Object> dashSupervisor = new HashMap<>();
                dashSupervisor.put("dadosSupervisor", supervisorService.toResponseDTO(supervisor.get()));

                if (supervisor.get().getEmpresa() != null) {
                    Long empresaId = supervisor.get().getEmpresa().getId();
                    List<Estagiario> listaEstagiarios = estagiarioRepository.findByEmpresaId(empresaId);

                    dashSupervisor.put("estagiarios", listaEstagiarios.stream().map(estagiarioService::toResponseDTO).toList());
                    dashSupervisor.put("totalEstagiarios", listaEstagiarios.size());

                    long totalAv = avaliacaoRepository.countBySupervisorId(supervisor.get().getId());
                    dashSupervisor.put("totalAvaliacoes", totalAv);
                } else {
                    dashSupervisor.put("estagiarios", Collections.emptyList());
                    dashSupervisor.put("totalEstagiarios", 0);
                    dashSupervisor.put("totalAvaliacoes", 0);
                }

                response.put("dashboardSupervisor", dashSupervisor);
                return ResponseEntity.ok(response);
            }

            // --- 3. COORDENADOR ---
            Optional<Coordenador> coordenador = coordenadorRepository.findByEmail(email);
            if (coordenador.isEmpty()) coordenador = coordenadorRepository.findByEmail(authentication.getName());

            if (coordenador.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("role", "COORDENADOR");

                Map<String, Object> dashCoordenador = new HashMap<>();
                dashCoordenador.put("dadosCoordenador", coordenadorService.toResponseDTO(coordenador.get()));

                if (coordenador.get().getFaculdade() != null) {
                    dashCoordenador.put("dadosFaculdade", coordenadorService.toFaculdadeResponseDTO(coordenador.get().getFaculdade()));
                    Long faculdadeId = coordenador.get().getFaculdade().getId();
                    List<Estagiario> alunos = estagiarioRepository.findByDadosAcademicos_Faculdade_Id(faculdadeId);

                    dashCoordenador.put("estagiarios", alunos.stream().map(estagiarioService::toResponseDTO).toList());
                    dashCoordenador.put("totalEstagiarios", alunos.size());

                    // Métricas Reais
                    long totalEmpresas = alunos.stream()
                            .map(a -> a.getEmpresa() != null ? a.getEmpresa().getId() : null)
                            .filter(Objects::nonNull)
                            .distinct()
                            .count();
                    dashCoordenador.put("totalEmpresasParceiras", totalEmpresas);

                    long totalAvaliacoes = 0;
                    double somaNotas = 0;
                    int countNotas = 0;

                    for (Estagiario aluno : alunos) {
                        List<Avaliacao> avs = avaliacaoRepository.findByEstagiarioId(aluno.getId());
                        totalAvaliacoes += avs.size();
                        for (Avaliacao av : avs) {
                            somaNotas += (av.getNotaDesempenho() + av.getNotaHabilidadesTecnicas() + av.getNotaHabilidadesComportamentais()) / 3.0;
                            countNotas++;
                        }
                    }
                    dashCoordenador.put("totalAvaliacoesRecebidas", totalAvaliacoes);
                    dashCoordenador.put("mediaGeralNotas", countNotas > 0 ? String.format("%.1f", somaNotas / countNotas).replace(',', '.') : "0.0");

                } else {
                    dashCoordenador.put("totalEstagiarios", 0);
                    dashCoordenador.put("totalEmpresasParceiras", 0);
                    dashCoordenador.put("totalAvaliacoesRecebidas", 0);
                    dashCoordenador.put("mediaGeralNotas", 0);
                }

                response.put("dashboardCoordenador", dashCoordenador);
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Perfil não encontrado.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

    private List<Map<String, String>> gerarConquistas(List<Competencia> skills, List<?> avaliacoes, boolean temContrato) {
        List<Map<String, String>> lista = new ArrayList<>();

        if (!skills.isEmpty()) {
            lista.add(Map.of("nome", "Aprendiz dedicado", "descricao", "Cadastrou suas primeiras competências.", "icone", "Rocket"));
        }
        if (skills.size() > 3) {
            lista.add(Map.of("nome", "Multitarefa", "descricao", "Domina mais de 3 habilidades técnicas.", "icone", "Layers"));
        }
        if (avaliacoes != null && !avaliacoes.isEmpty()) {
            lista.add(Map.of("nome", "Primeiro feedback", "descricao", "Recebeu a primeira avaliação oficial.", "icone", "MessageCircle"));
        }
        if (temContrato) {
            lista.add(Map.of("nome", "Contrato fechado", "descricao", "Dados de estágio 100% preenchidos.", "icone", "Briefcase"));
        }

        return lista;
    }
}