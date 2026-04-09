package com.myapp.config;

import com.myapp.model.User;
import com.myapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Profile("dev")
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            logger.info("Seeding database with default users...");
            // Admin user
            User admin = new User();
            admin.setId(UUID.randomUUID());
            admin.setUsername("admin");
            admin.setEmail("admin@myapp.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles("ROLE_ADMIN,ROLE_USER");
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);
            logger.info("Created admin user: admin / admin123");

            // Regular user
            User user = new User();
            user.setId(UUID.randomUUID());
            user.setUsername("user");
            user.setEmail("user@myapp.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRoles("ROLE_USER");
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
            logger.info("Created regular user: user / user123");

            logger.info("✅ Database seeding completed!");
        } else {
            logger.info("Database already contains users. Skipping seeding.");
        }
    }
}