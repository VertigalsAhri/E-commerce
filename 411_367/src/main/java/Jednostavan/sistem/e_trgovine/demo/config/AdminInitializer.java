package Jednostavan.sistem.e_trgovine.demo.config;

import Jednostavan.sistem.e_trgovine.demo.model.User;
import Jednostavan.sistem.e_trgovine.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("Kreiran default admin korisnik - username: 'admin', password: 'admin'");
        } else {
            System.out.println("Admin korisnik već postoji, preskačem kreiranje.");
        }
    }
} 