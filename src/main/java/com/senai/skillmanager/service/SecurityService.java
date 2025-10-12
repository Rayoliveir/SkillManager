package com.senai.skillmanager.service;

import com.senai.skillmanager.model.estagiario.Estagiario;
import com.senai.skillmanager.model.faculdade.Faculdade;
import com.senai.skillmanager.model.funcionario.Funcionario;
import com.senai.skillmanager.repository.EstagiarioRepository;
import com.senai.skillmanager.repository.FaculdadeRepository;
import com.senai.skillmanager.repository.FuncionarioRepository;
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
    private final FuncionarioRepository funcionarioRepository;
    private final FaculdadeRepository faculdadeRepository;

    public SecurityService(EstagiarioRepository estagiarioRepository, FuncionarioRepository funcionarioRepository, FaculdadeRepository faculdadeRepository) {
        this.estagiarioRepository = estagiarioRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.faculdadeRepository = faculdadeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Estagiario> estagiarioOpt = estagiarioRepository.findByEmail(username);
        if (estagiarioOpt.isPresent()) {
            Estagiario estagiario = estagiarioOpt.get();
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ESTAGIARIO"));
            return new User(estagiario.getEmail(), estagiario.getSenha(), authorities);
        }

        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findByEmail(username);
        if (funcionarioOpt.isPresent()) {
            Funcionario funcionario = funcionarioOpt.get();
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + funcionario.getCargo().name()));
            return new User(funcionario.getEmail(), funcionario.getSenha(), authorities);
        }

        Optional<Faculdade> faculdadeOpt = faculdadeRepository.findByEmail(username);
        if (faculdadeOpt.isPresent()) {
            Faculdade faculdade = faculdadeOpt.get();
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_FACULDADE"));
            return new User(faculdade.getEmail(), faculdade.getSenha(), authorities);
        }

        throw new UsernameNotFoundException("Usuário não encontrado com o email: " + username);
    }
}