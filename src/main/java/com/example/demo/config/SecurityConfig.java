package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

/**
 * Configuration Spring Security.
 *
 * Politique :
 *  - GET  publics  : lecture catalogue et parties
 *  - POST / DELETE : HTTP Basic requis
 *  - Console H2    : accessible uniquement si le profil "h2" est actif (dev local)
 *
 * Credentials via variables d'environnement :
 *   API_USERNAME  (défaut : admin)
 *   API_PASSWORD  — OBLIGATOIRE
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Environment env;

    public SecurityConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, RateLimitFilter rateLimitFilter) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> {
                // GET publics
                auth.requestMatchers(HttpMethod.GET, "/games/catalog/**").permitAll();
                auth.requestMatchers(HttpMethod.GET, "/api/games/**").permitAll();

                // Console H2 : dev local uniquement (profil "h2" actif)
                if (Arrays.asList(env.getActiveProfiles()).contains("h2")) {
                    auth.requestMatchers("/h2-console/**").permitAll();
                }

                // Tout le reste nécessite une authentification
                auth.anyRequest().authenticated();
            })
            .httpBasic(httpBasic -> {})
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        String username = System.getenv().getOrDefault("API_USERNAME", "admin");
        String rawPassword = System.getenv("API_PASSWORD");

        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalStateException(
                "La variable d'environnement API_PASSWORD est obligatoire.");
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
