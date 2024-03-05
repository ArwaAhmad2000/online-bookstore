package com.example.onlinebookstore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtGenerator jwtGenerator;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(
                httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .exceptionHandling(AuthenticationEntryPoint -> AuthenticationEntryPoint
                        .authenticationEntryPoint(jwtAuthEntryPoint))
                .authorizeHttpRequests(authRequest -> {
                    authRequest.requestMatchers("/api/v1/auth/**").permitAll();
                    authRequest.requestMatchers("/api/v1/users/**").hasAuthority("ADMIN");
                    authRequest.requestMatchers(HttpMethod.POST, "/api/v1/books").hasAuthority("ADMIN");
                    authRequest.requestMatchers(HttpMethod.PATCH, "/api/v1/books").hasAuthority("ADMIN");
                    authRequest.requestMatchers(HttpMethod.DELETE, "/api/v1/books").hasAuthority("ADMIN");
                    authRequest.requestMatchers("/**").authenticated();
                })
                .httpBasic(Customizer.withDefaults());
        http.addFilterBefore(new JwtAuthenticationFilter(jwtGenerator, customUserDetailsService),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtGenerator, customUserDetailsService);
    }
}
