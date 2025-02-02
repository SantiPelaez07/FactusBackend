package com.retoFactus.factus.config;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.retoFactus.factus.domain.entities.User;
import com.retoFactus.factus.domain.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Service
@Builder
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByNameUser(username).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        return org.springframework.security.core.userdetails.User.builder()
        .username(user.getNameUser())
        .password(user.getPassword())
        .authorities(user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())).collect(Collectors.toList())).build();
    }

}
