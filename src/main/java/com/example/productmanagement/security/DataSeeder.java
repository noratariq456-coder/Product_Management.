package com.example.productmanagement.security;

import com.example.productmanagement.entity.AppUser;
import com.example.productmanagement.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection
    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(new AppUser("admin", passwordEncoder.encode("admin123"), "ADMIN"));
            userRepository.save(new AppUser("manager", passwordEncoder.encode("manager123"), "MANAGER"));
            userRepository.save(new AppUser("employee", passwordEncoder.encode("employee123"), "EMPLOYEE"));
            System.out.println(">>> Seeded default users: admin/admin123, manager/manager123, employee/employee123");
        }
    }
}
