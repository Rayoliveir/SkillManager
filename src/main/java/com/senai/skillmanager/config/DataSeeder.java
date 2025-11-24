package com.senai.skillmanager.config;

import com.senai.skillmanager.model.Admin;
import com.senai.skillmanager.model.Endereco;
import com.senai.skillmanager.model.Estados;
import com.senai.skillmanager.model.Genero;
import com.senai.skillmanager.model.empresa.Cargo;
import com.senai.skillmanager.model.empresa.Empresa;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.empresa.TipoEmpresa;
import com.senai.skillmanager.model.estagiario.DadosAcademicos;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;

@Configuration
public class DataSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final EmpresaRepository empresaRepository;
    private final FaculdadeRepository faculdadeRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final SupervisorRepository supervisorRepository;
    private final CoordenadorRepository coordenadorRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(AdminRepository adminRepository,
                      EmpresaRepository empresaRepository,
                      FaculdadeRepository faculdadeRepository,
                      EstagiarioRepository estagiarioRepository,
                      SupervisorRepository supervisorRepository,
                      CoordenadorRepository coordenadorRepository,
                      PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.empresaRepository = empresaRepository;
        this.faculdadeRepository = faculdadeRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.supervisorRepository = supervisorRepository;
        this.coordenadorRepository = coordenadorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // --- 1. ADMIN ---
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setNome("Admin");
            admin.setEmail("admin@skillmanager.com");
            admin.setSenha(passwordEncoder.encode("admin123"));
            adminRepository.save(admin);
        }

        // --- 2. DEPENDÊNCIAS (EMPRESA E FACULDADE) ---
        Empresa empresaTeste;
        if (empresaRepository.findByCodigoEmpresa("EMP-TESTE").isEmpty()) {
            empresaTeste = new Empresa();
            empresaTeste.setNome("Empresa Teste SA");
            empresaTeste.setRazaoSocial("Empresa Teste S/A");
            empresaTeste.setCnpj("11111111000111");
            empresaTeste.setCodigoEmpresa("EMP-TESTE");
            empresaTeste.setTipoEmpresa(TipoEmpresa.SERVICO); // <-- CORRIGIDO

            Endereco endEmpresa = new Endereco();
            endEmpresa.setLogradouro("Rua da Empresa Teste");
            endEmpresa.setNumero("100");
            endEmpresa.setBairro("Centro");
            endEmpresa.setCidade("São Paulo");
            endEmpresa.setEstados(Estados.SP);
            endEmpresa.setCep("01000000");
            empresaTeste.setEndereco(endEmpresa);
            empresaTeste = empresaRepository.save(empresaTeste);
        } else {
            empresaTeste = empresaRepository.findByCodigoEmpresa("EMP-TESTE").get();
        }

        Faculdade faculdadeTeste;
        if (faculdadeRepository.findByCnpj("22222222000122").isEmpty()) {
            faculdadeTeste = new Faculdade();
            faculdadeTeste.setNome("Faculdade Teste de Tecnologia");
            faculdadeTeste.setCnpj("22222222000122");
            faculdadeTeste.setSite("www.faculdadeteste.edu.br");
            faculdadeTeste.setTelefone("11999999999");

            Endereco endFaculdade = new Endereco();
            endFaculdade.setLogradouro("Avenida da Faculdade Teste");
            endFaculdade.setNumero("200");
            endFaculdade.setBairro("Universitário");
            endFaculdade.setCidade("Campinas");
            endFaculdade.setEstados(Estados.SP);
            endFaculdade.setCep("13000000");
            faculdadeTeste.setEndereco(endFaculdade);
            faculdadeTeste = faculdadeRepository.save(faculdadeTeste);
        } else {
            faculdadeTeste = faculdadeRepository.findByCnpj("22222222000122").get();
        }

        // --- 3. UTILIZADORES DE TESTE ---

        // ESTAGIÁRIO (Login: estagiario@teste.com / Senha: 123456)
        if (estagiarioRepository.findByEmail("estagiario@teste.com").isEmpty()) {
            Estagiario estagiario = new Estagiario();
            estagiario.setNome("Bruno Estagiário Teste");
            estagiario.setEmail("estagiario@teste.com");
            estagiario.setSenha(passwordEncoder.encode("123456"));
            estagiario.setCpf("11122233344");
            estagiario.setDataNascimento(LocalDate.of(2002, 5, 10));
            estagiario.setGenero(Genero.MASCULINO);
            estagiario.setTelefone("11988887777");
            estagiario.setEmpresa(empresaTeste);

            Endereco endEstagiario = new Endereco();
            endEstagiario.setLogradouro("Rua do Estagiário");
            endEstagiario.setNumero("1");
            endEstagiario.setBairro("Vila Teste");
            endEstagiario.setCidade("São Paulo");
            endEstagiario.setEstados(Estados.SP);
            endEstagiario.setCep("02000000");
            estagiario.setEndereco(endEstagiario);

            DadosAcademicos dados = new DadosAcademicos();
            dados.setFaculdade(faculdadeTeste);
            dados.setCurso("Análise e Desenvolvimento de Sistemas");
            dados.setPeriodoSemestre("3º Semestre");
            dados.setPrevisaoFormatura(YearMonth.of(2026, 12));
            dados.setRa("123456789");
            estagiario.setDadosAcademicos(dados);

            estagiarioRepository.save(estagiario);
        }

        // SUPERVISOR (Login: supervisor@teste.com / Senha: 123456)
        if (supervisorRepository.findByEmail("supervisor@teste.com").isEmpty()) {
            Supervisor supervisor = new Supervisor();
            supervisor.setNome("Carla Supervisora Teste");
            supervisor.setEmail("supervisor@teste.com");
            supervisor.setSenha(passwordEncoder.encode("123456"));
            supervisor.setCargo(Cargo.SUPERVISOR);
            // supervisor.setDepartamento("TI"); // <-- REMOVIDO (Não existe no Model)
            supervisor.setEmpresa(empresaTeste);

            supervisorRepository.save(supervisor);
        }

        // GERENTE (Login: gerente@teste.com / Senha: 123456)
        if (supervisorRepository.findByEmail("gerente@teste.com").isEmpty()) {
            Supervisor gerente = new Supervisor();
            gerente.setNome("Ricardo Gerente Teste");
            gerente.setEmail("gerente@teste.com");
            gerente.setSenha(passwordEncoder.encode("123456"));
            gerente.setCargo(Cargo.GERENTE); // <- Role diferente
            // gerente.setDepartamento("TI"); // <-- REMOVIDO (Não existe no Model)
            gerente.setEmpresa(empresaTeste);

            supervisorRepository.save(gerente);
        }

        // COORDENADOR (Login: coordenador@teste.com / Senha: 123456)
        if (coordenadorRepository.findByEmail("coordenador@teste.com").isEmpty()) {
            Coordenador coordenador = new Coordenador();
            coordenador.setNome("Prof. Dr. Coordenador Teste");
            coordenador.setEmail("coordenador@teste.com");
            coordenador.setSenha(passwordEncoder.encode("123456"));
            coordenador.setFaculdade(faculdadeTeste);

            coordenadorRepository.save(coordenador);
        }
    }
}