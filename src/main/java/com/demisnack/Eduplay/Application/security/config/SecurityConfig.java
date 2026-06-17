package com.demisnack.Eduplay.Application.security.config;

import com.demisnack.Eduplay.Application.global.exception.CustomAuthenticationEntryPoint;
import com.demisnack.Eduplay.Application.global.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF karena kita pakai JWT
                .csrf(AbstractHttpConfigurer::disable)

                // Aturan Autorisasi Endpoint
                .authorizeHttpRequests(auth -> auth
                        // Pintu masuk Auth (tanpa refresh token)
                        .requestMatchers("/api/auth/register", "/api/auth/login", "/error").permitAll()

                        // Marketplace dibuka publik biar user bisa explore
                        .requestMatchers("/api/catalog/**").permitAll()

                        // Sisanya (Generator, Profile, Contributor) wajib login
                        .anyRequest().authenticated()
                )

                // Custom respon error kalau token tidak valid/kosong
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                )

                // Pastikan session tidak disimpan di server (Stateless)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Eksekusi filter JWT sebelum filter bawaan Spring Security
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}