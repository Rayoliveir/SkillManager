package com.senai.skillmanager.service;

import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
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
    private final SupervisorRepository supervisorRepository;
    private final CoordenadorRepository coordenadorRepository;

    public SecurityService(EstagiarioRepository estagiarioRepository, SupervisorRepository supervisorRepository, CoordenadorRepository coordenadorRepository) {
        this.estagiarioRepository = estagiarioRepository;
        this.supervisorRepository = supervisorRepository;
        this.coordenadorRepository = coordenadorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Estagiario> estagiarioOpt = estagiarioRepository.findByEmail(username);
        if (estagiarioOpt.isPresent()) {
            Estagiario estagiario = estagiarioOpt.get();
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"));
            return new User(estagiario.getEmail(), estagiario.getSenha(), authorities);
        }
        Optional<Supervisor> supervisorOpt = supervisorRepository.findByEmail(username);

        if (supervisorOpt.isPresent()) {
            Supervisor supervisor = supervisorOpt.get();
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + supervisor.getCargo().name()));
            return new User(supervisor.getEmail(), supervisor.getSenha(), authorities);
        }

        Optional<Coordenador> coordenadorOpt = coordenadorRepository.findByEmail(username);
        if (coordenadorOpt.isPresent()) {
            Coordenador coordenador = coordenadorOpt.get();
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_FACULDADE"));
            return new User(coordenador.getEmail(), coordenador.getSenha(), authorities);
        }

        throw new UsernameNotFoundException("Usuário não encontrado com o email: " + username);
    }
}