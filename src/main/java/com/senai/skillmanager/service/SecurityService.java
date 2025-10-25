package com.senai.skillmanager.service;

import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador; // Importado o model Coordenador
import com.senai.skillmanager.model.empresa.Supervisor; // Importado o model Supervisor
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.CoordenadorRepository; // Repositório atualizado
import com.senai.skillmanager.repository.SupervisorRepository; // Repositório atualizado
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecurityService implements UserDetailsService {

    private final EstagiarioRepository estagiarioRepository;
    private final SupervisorRepository supervisorRepository; // Repositório atualizado
    private final CoordenadorRepository coordenadorRepository; // Repositório atualizado

    public SecurityService(EstagiarioRepository estagiarioRepository, SupervisorRepository supervisorRepository, CoordenadorRepository coordenadorRepository) {
        this.estagiarioRepository = estagiarioRepository;
        this.supervisorRepository = supervisorRepository;
        this.coordenadorRepository = coordenadorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Tenta encontrar como Estagiário
        Optional<Estagiario> estagiarioOpt = estagiarioRepository.findByEmail(username);
        if (estagiarioOpt.isPresent()) {
            Estagiario estagiario = estagiarioOpt.get();
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"));
            return new User(estagiario.getEmail(), estagiario.getSenha(), authorities);
        }

        // 2. Tenta encontrar como Supervisor (antigo Funcionario)
        Optional<Supervisor> supervisorOpt = supervisorRepository.findByEmail(username);
        if (supervisorOpt.isPresent()) {
            Supervisor supervisor = supervisorOpt.get();
            // A role vem do Enum Cargo (ex: ROLE_ADMIN, ROLE_SUPERVISOR)
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + supervisor.getCargo().name()));
            return new User(supervisor.getEmail(), supervisor.getSenha(), authorities);
        }

        // 3. Tenta encontrar como Coordenador (antigo Faculdade)
        Optional<Coordenador> coordenadorOpt = coordenadorRepository.findByEmail(username);
        if (coordenadorOpt.isPresent()) {
            Coordenador coordenador = coordenadorOpt.get();
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_FACULDADE"));
            return new User(coordenador.getEmail(), coordenador.getSenha(), authorities);
        }

        // Se não encontrou ninguém
        throw new UsernameNotFoundException("Usuário não encontrado com o email: " + username);
    }
}