package com.fleet.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Aceasta este "unealta" care crypteaza parolele
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Aici dezactivam protectia automata pentru a putea lucra linistiti momentan
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Necesar pentru POST request-uri
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Lasam totul liber momentan (pt testare)
                );
        return http.build();
    }
}