package com.senai.skillmanager.config;

import com.senai.skillmanager.model.*;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;

@Component
public class DataSeeder implements CommandLineRunner {

    private final SupervisorRepository supervisorRepository;
    private final EmpresaRepository empresaRepository;
    private final CoordenadorRepository coordenadorRepository;
    private final FaculdadeRepository faculdadeRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(SupervisorRepository supervisorRepository,
                      EmpresaRepository empresaRepository,
                      CoordenadorRepository coordenadorRepository,
                      FaculdadeRepository faculdadeRepository,
                      EstagiarioRepository estagiarioRepository,
                      PasswordEncoder passwordEncoder) {
        this.supervisorRepository = supervisorRepository;
        this.empresaRepository = empresaRepository;
        this.coordenadorRepository = coordenadorRepository;
        this.faculdadeRepository = faculdadeRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Só roda se não tiver ninguém cadastrado
        if (empresaRepository.count() == 0) {
            seedTudo();
        }
    }

    private void seedTudo() {
        System.out.println("🌱 [DataSeeder] Iniciando população do banco de dados...");

        // 1. Cria a Empresa
        Empresa empresa = new Empresa();
        empresa.setNome("Innovatech Solutions");
        empresa.setRazaoSocial("Innovatech Desenvolvimento de Software Ltda");
        empresa.setCnpj("43267890000155");
        empresa.setTipoEmpresa(TipoEmpresa.SERVICO);
        empresa.setInscricaoMunicipal("987654321");
        empresa.setCodigoEmpresa("F6F-50A");
        empresa.setEndereco(criarEndereco("Av. Paulista", "1000", "Bela Vista", "São Paulo", "SP", "01310100"));
        empresaRepository.save(empresa);

        // 2. Cria o Supervisor
        Supervisor supervisor = new Supervisor();
        supervisor.setNome("Roberto Mendes");
        supervisor.setEmail("roberto.mendes@innovatech.com");
        supervisor.setSenha(passwordEncoder.encode("senha123"));
        supervisor.setCargo(Cargo.GERENTE);
        supervisor.setEmpresa(empresa);
        supervisorRepository.save(supervisor);

        // 3. Cria a Faculdade
        Faculdade faculdade = new Faculdade();
        faculdade.setNome("UniFatec Tecnologia");
        faculdade.setCnpj("98765432000100");
        faculdade.setTelefone("1140028922");
        faculdade.setSite("www.unifatec.edu.br");
        faculdade.setEndereco(criarEndereco("Rua dos Estudantes", "500", "Liberdade", "São Paulo", "SP", "01500000"));
        faculdadeRepository.save(faculdade);

        // 4. Cria o Coordenador
        Coordenador coordenador = new Coordenador();
        coordenador.setNome("Prof. Regina Volpato");
        coordenador.setEmail("regina.volpato@unifatec.edu.br");
        coordenador.setSenha(passwordEncoder.encode("senha123"));
        coordenador.setFaculdade(faculdade);
        coordenadorRepository.save(coordenador);

        // 5. Cria os 10 Estagiários
        criarEstagiario("Lucas Silva", "lucas.silva@aluno.unifuturo.edu.br", "62639462002", "2001-05-15", Genero.MASCULINO, "11988881111",
                empresa, faculdade, "Engenharia de Software", 6, "2025-12", "RA1001", "Rua A", "10", "Centro");

        criarEstagiario("Mariana Costa", "mariana.costa@aluno.unifuturo.edu.br", "19565628003", "2002-08-20", Genero.FEMININO, "11988882222",
                empresa, faculdade, "Sistemas de Informação", 4, "2026-06", "RA1002", "Rua B", "20", "Vila Madalena");

        criarEstagiario("Pedro Santos", "pedro.santos@aluno.unifuturo.edu.br", "23793613009", "2000-01-10", Genero.MASCULINO, "11988883333",
                empresa, faculdade, "Ciência da Computação", 8, "2024-12", "RA1003", "Rua C", "30", "Moema");

        criarEstagiario("Julia Ferreira", "julia.ferreira@aluno.unifuturo.edu.br", "42363063060", "2003-03-25", Genero.FEMININO, "11988884444",
                empresa, faculdade, "Análise e Desenvolvimento de Sistemas", 2, "2026-12", "RA1004", "Rua D", "40", "Pinheiros");

        criarEstagiario("Rafael Oliveira", "rafael.oliveira@aluno.unifuturo.edu.br", "05244569032", "1999-11-05", Genero.MASCULINO, "11988885555",
                empresa, faculdade, "Engenharia de Computação", 9, "2024-06", "RA1005", "Rua E", "50", "Lapa");

        criarEstagiario("Beatriz Lima", "beatriz.lima@aluno.unifuturo.edu.br", "37207865099", "2002-06-12", Genero.FEMININO, "11999996666",
                empresa, faculdade, "Design Digital", 5, "2025-12", "RA1006", "Rua F", "60", "Tatuapé");

        criarEstagiario("Gustavo Rocha", "gustavo.rocha@aluno.unifuturo.edu.br", "02693987002", "2001-09-30", Genero.MASCULINO, "11999997777",
                empresa, faculdade, "Segurança da Informação", 3, "2026-06", "RA1007", "Rua G", "70", "Mooca");

        criarEstagiario("Camila Martins", "camila.martins@aluno.unifuturo.edu.br", "52984136000", "2003-02-14", Genero.FEMININO, "11999998888",
                empresa, faculdade, "Marketing Digital", 4, "2025-12", "RA1008", "Rua H", "80", "Santana");

        criarEstagiario("Felipe Souza", "felipe.souza@aluno.unifuturo.edu.br", "22157303027", "2000-07-05", Genero.MASCULINO, "11999999999",
                empresa, faculdade, "Redes de Computadores", 2, "2026-12", "RA1009", "Rua I", "90", "Saúde");

        criarEstagiario("Larissa Alves", "larissa.alves@aluno.unifuturo.edu.br", "96229977050", "2002-11-22", Genero.FEMININO, "11999990000",
                empresa, faculdade, "Gestão de TI", 6, "2024-12", "RA1010", "Rua J", "100", "Ipiranga");

        System.out.println("✅ [DataSeeder] Banco populado com sucesso!");
    }

    // --- Métodos Auxiliares ---

    private Endereco criarEndereco(String log, String num, String bairro, String cid, String uf, String cep) {
        Endereco e = new Endereco();
        e.setLogradouro(log);
        e.setNumero(num);
        e.setBairro(bairro);
        e.setCidade(cid);

        // --- CORREÇÃO 1: Converte String para Enum Estados ---
        e.setEstados(Estados.valueOf(uf));

        e.setCep(cep);
        return e;
    }

    private void criarEstagiario(String nome, String email, String cpf, String dataNasc, Genero genero, String tel,
                                 Empresa empresa, Faculdade faculdade, String curso, int semestre, String prevForm, String ra,
                                 String rua, String num, String bairro) {

        Estagiario est = new Estagiario();
        est.setNome(nome);
        est.setEmail(email);
        est.setCpf(cpf);
        est.setSenha(passwordEncoder.encode("senha123"));
        est.setDataNascimento(LocalDate.parse(dataNasc));
        est.setGenero(genero);
        est.setTelefone(tel);
        est.setEmpresa(empresa);
        est.setEndereco(criarEndereco(rua, num, bairro, "São Paulo", "SP", "01000000"));

        DadosAcademicos da = new DadosAcademicos();
        da.setCurso(curso);

        // --- CORREÇÃO 2: Converte int para String ---
        da.setPeriodoSemestre(String.valueOf(semestre));

        da.setPrevisaoFormatura(YearMonth.parse(prevForm));
        da.setRa(ra);
        da.setFaculdade(faculdade);

        est.setDadosAcademicos(da);

        estagiarioRepository.save(est);
    }
}