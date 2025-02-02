package com.retoFactus.factus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager AuthenticationManager(HttpSecurity http)throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder()).and().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
        .requestMatchers("/factus/api/v1/auth/register").permitAll()
        .requestMatchers("/factus/api/v1/admin/**").hasRole("SUPER_ADMIN")
        .anyRequest().authenticated()).formLogin(Customizer.withDefaults())
        .userDetailsService(userDetailsService);
        return http.build();
    }
}
