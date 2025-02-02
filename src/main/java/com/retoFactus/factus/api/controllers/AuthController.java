package com.retoFactus.factus.api.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retoFactus.factus.api.request.UserRequest;
import com.retoFactus.factus.domain.entities.Role;
import com.retoFactus.factus.domain.entities.User;
import com.retoFactus.factus.domain.repositories.RoleRepository;
import com.retoFactus.factus.domain.repositories.UserRepository;
import com.retoFactus.factus.infrastructure.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @PostMapping(path = "/login")
    public String authenticate(@RequestParam String username, @RequestParam String password){
        org.springframework.security.core.Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "Autenticación Exitosa";
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest request){
        try{
            System.out.println("Accedo al controlador.");
            if (this.userRepository.existsByNameUser(request.getNameUser())) {
                return ResponseEntity.badRequest().body("El usuario ya existe.");
            }
            Role userRole = this.roleRepository.findByName("USER").orElseThrow(() -> new RuntimeException("Rol USER no encontrado."));
            User user = this.userService.createdUserRegister(request, userRole);
            if (user != null) {
                return ResponseEntity.ok("Usuario registrado correctamente.");
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario");
            }

        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar el usuario");
        }
    }
}
