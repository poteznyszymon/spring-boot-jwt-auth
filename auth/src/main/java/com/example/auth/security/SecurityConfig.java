package com.example.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
public class SecurityConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthEntryPoint customAuthEntryPoint;

    public SecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter, CustomUserDetailsService customUserDetailsService, CustomAuthEntryPoint customAuthEntryPoint) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthEntryPoint = customAuthEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req.requestMatchers("/api/auth/me").authenticated()
                                .requestMatchers("/api/auth/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/restaurant/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/review/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                ).userDetailsService(customUserDetailsService)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthEntryPoint))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
