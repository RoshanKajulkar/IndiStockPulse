package com.example.indistockpulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for simplicity (not recommended for production)
                .authorizeRequests()
                .requestMatchers("/hello").permitAll() // Allow access to the /hello endpoint
                .anyRequest().authenticated(); // Require authentication for any other requests
        return http.build();
    }
}
