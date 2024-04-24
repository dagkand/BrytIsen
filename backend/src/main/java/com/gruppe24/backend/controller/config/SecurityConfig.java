package com.gruppe24.backend.controller.config;

import com.gruppe24.backend.controller.component.CustomAuthenticationSuccessHandler;
import com.gruppe24.backend.controller.component.JwtTokenFilter;
import com.gruppe24.backend.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserRepository userRepository;

  public SecurityConfig(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(cors -> cors.configurationSource(request -> corsConfigurationSource()))
            .authorizeHttpRequests(auth -> {
              auth.requestMatchers("/", "/error").permitAll();
              auth.requestMatchers("/games", "/games/**").permitAll();
              auth.requestMatchers("/users/isLoggedIn").permitAll();
              auth.requestMatchers("/lists/{id}/view", "lists/{ID}/games").permitAll();
              auth.requestMatchers("/games/create", "/games/{id}/delete", "/games/{ID}/update").authenticated();
              auth.requestMatchers("/registration").authenticated();
              auth.anyRequest().authenticated();
            })
            .addFilterBefore(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .oauth2Login(oauth2 -> oauth2.successHandler(new CustomAuthenticationSuccessHandler(userRepository)))
            .csrf(AbstractHttpConfigurer::disable)
            .build();
  }

  @Bean
  public CorsConfiguration corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8080"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
    configuration.setExposedHeaders(List.of("x-auth-token"));
    configuration.setAllowCredentials(true);
    return configuration;
  }

}