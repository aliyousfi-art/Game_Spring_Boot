package com.example.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration Spring Security pour users-service.
 *
 * Politique :
 *  - GET /users/{id}/valid : public (vérifié par le service principal)
 *  - Tout le reste         : HTTP Basic requis
 *
 * CSRF désactivé car API stateless (pas de session, pas de cookies).
 *
 * Credentials via variables d'environnement :
 *   USERS_API_USERNAME  (défaut : admin)
 *   USERS_API_PASSWORD  — OBLIGATOIRE
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF désactivé intentionnellement : API REST stateless (tokens HTTP Basic, pas de cookies)
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                // Endpoint de validation exposé au service principal (inter-service)
                auth.requestMatchers(HttpMethod.GET, "/users/*/valid").permitAll();
                // Tout le reste requiert une authentification
                auth.anyRequest().authenticated();
            })
            .httpBasic(httpBasic -> {});

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        String username = System.getenv().getOrDefault("USERS_API_USERNAME", "admin");
        String rawPassword = System.getenv("USERS_API_PASSWORD");

        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalStateException(
                "La variable d'environnement USERS_API_PASSWORD est obligatoire.");
        }

        return new InMemoryUserDetailsManager(
            User.withUsername(username)
                .password(passwordEncoder.encode(rawPassword))
                .roles("API")
                .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
