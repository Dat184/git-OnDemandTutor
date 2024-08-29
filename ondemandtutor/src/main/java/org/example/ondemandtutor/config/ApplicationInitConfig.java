package org.example.ondemandtutor.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.ondemandtutor.pojo.Admin;
import org.example.ondemandtutor.pojo.Role;
import org.example.ondemandtutor.pojo.User;
import org.example.ondemandtutor.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j //logger
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {

                User user = new Admin();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setRole(Role.Admin);
                user.setEmail("Admin@gmail.com");
                user.setName("Admin");
                userRepository.save(user);
                log.info("admin added");

            }

        };
    }
}
