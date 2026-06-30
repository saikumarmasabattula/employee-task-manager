package com.employee.taskmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaAuditing
@SpringBootApplication
public class TaskmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskmanagerApplication.class, args);
    }
    @Bean
    CommandLineRunner testPassword(PasswordEncoder passwordEncoder) {
        return args -> {
            System.out.println(
                    passwordEncoder.matches(
                            "Sai@14052005",
                            "$2a$10$VTlY9JeXROxh9m5K5CxS4uXW/3noADDU.EeBPKGch3p8vD9CzcE/W"
                    )
            );
        };
    }

}