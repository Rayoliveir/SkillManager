package com.senai.skillmanager.config;

import com.senai.skillmanager.model.Admin;
import com.senai.skillmanager.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        seedAdminUser();
    }

    private void seedAdminUser() {
        if (adminRepository.count() == 0) {
            Admin defaultAdmin = new Admin();
            defaultAdmin.setNome("Administrador padrão");
            defaultAdmin.setEmail("admin@skillmanager.com");
            defaultAdmin.setSenha(passwordEncoder.encode("admin123"));

            adminRepository.save(defaultAdmin);
            System.out.println("Usuário administrador padrão criado com sucesso.");
        }
    }
}