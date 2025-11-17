package com.senai.skillmanager.service;

import com.senai.skillmanager.model.Admin;
import com.senai.skillmanager.model.empresa.Cargo;
import com.senai.skillmanager.model.empresa.Supervisor;
import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Coordenador;
import com.senai.skillmanager.repository.AdminRepository;
import com.senai.skillmanager.repository.CoordenadorRepository;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.SupervisorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SecurityService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final EstagiarioRepository estagiarioRepository;
    private final SupervisorRepository supervisorRepository;
    private final CoordenadorRepository coordenadorRepository;

    public SecurityService(AdminRepository adminRepository,
                           EstagiarioRepository estagiarioRepository,
                           SupervisorRepository supervisorRepository,
                           CoordenadorRepository coordenadorRepository) {
        this.adminRepository = adminRepository;
        this.estagiarioRepository = estagiarioRepository;
        this.supervisorRepository = supervisorRepository;
        this.coordenadorRepository = coordenadorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User(admin.getEmail(), admin.getSenha(), authorities);
        }

        Optional<Estagiario> estagiarioOpt = estagiarioRepository.findByEmail(email);
        if (estagiarioOpt.isPresent()) {
            Estagiario estagiario = estagiarioOpt.get();
            Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"));
            return new User(estagiario.getEmail(), estagiario.getSenha(), authorities);
        }

        Optional<Supervisor> supervisorOpt = supervisorRepository.findByEmail(email);
        if (supervisorOpt.isPresent()) {
            Supervisor supervisor = supervisorOpt.get();

            List<GrantedAuthority> authorities = new ArrayList<>();
            if (supervisor.getCargo() == Cargo.GERENTE) {
                authorities.add(new SimpleGrantedAuthority("ROLE_GERENTE"));
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
            }

            return new User(supervisor.getEmail(), supervisor.getSenha(), authorities);
        }

        Optional<Coordenador> coordenadorOpt = coordenadorRepository.findByEmail(email);
        if (coordenadorOpt.isPresent()) {
            Coordenador coordenador = coordenadorOpt.get();
            Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_FACULDADE"));
            return new User(coordenador.getEmail(), coordenador.getSenha(), authorities);
        }

        throw new UsernameNotFoundException("Usuário não encontrado com o email: " + email);
    }
}