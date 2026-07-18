package com.example.productmanagement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Custom property injected using @Value
    @Value("${app.custom.message}")
    private String customMessage;

    // One @Bean - prints the custom property at application startup
    @Bean
    public CommandLineRunner startupBanner() {
        return args -> System.out.println(">>> " + customMessage);
    }
}
