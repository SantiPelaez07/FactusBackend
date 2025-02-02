package com.retoFactus.factus.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retoFactus.factus.domain.entities.Role;
import com.retoFactus.factus.domain.entities.User;
import com.retoFactus.factus.domain.repositories.RoleRepository;
import com.retoFactus.factus.domain.repositories.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/admin")
@AllArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @PostMapping(path = "/assign-admin/{nameUser}")
    public ResponseEntity<?> assignAdminRole(@PathVariable String nameUser){
        User user = this.userRepository.findByNameUser(nameUser).orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        Role adminRole = this.roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado."));
        user.getRoles().add(adminRole);
        this.userRepository.save(user);
        return ResponseEntity.ok("El rol ADMIN ha sido asignado correctamente a "+ nameUser +".");
    }
}
