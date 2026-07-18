package com.example.productmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Public resources
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()

                        // REST API Security
                        .requestMatchers(HttpMethod.GET, "/api/products/**")
                        .hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/products/**")
                        .hasAnyRole("MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/products/**")
                        .hasAnyRole("MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/api/products/**")
                        .hasAnyRole("MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/api/products/**")
                        .hasRole("ADMIN")

                        // MVC Pages
                        .requestMatchers(HttpMethod.GET, "/products/**")
                        .hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/products/**")
                        .hasAnyRole("MANAGER", "ADMIN")

                        .anyRequest().authenticated()
                )

                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}