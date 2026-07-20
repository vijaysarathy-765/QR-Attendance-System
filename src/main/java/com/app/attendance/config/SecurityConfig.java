package com.app.attendance.config;

import com.app.attendance.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.http.HttpStatus;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                
                .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
                }))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/students/register",
                                "/students/login",
                                "/attendance-sessions/**",
                                "/attendance/**"
                        ).permitAll()

                        .anyRequest().authenticated())

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)

                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}