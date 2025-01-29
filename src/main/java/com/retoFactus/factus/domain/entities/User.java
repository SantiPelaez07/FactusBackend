package com.retoFactus.factus.domain.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;
    @Column(length = 15, nullable = false)
    private String nameUser;
    @Column(length = 20, nullable = false)
    private String lastNameUser;
    @Column(length = 15, nullable = false)
    private String dni;
    @Column(length = 40, nullable = false)
    private String departament;
    @Column(length = 80, nullable = false)
    private String address;
    @Column(length = 100, nullable = false)
    private String mail;
    @Column(length = 10, nullable = false)
    private String phone;

    // Relation with Invoice
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Invoice> invoices;
    
}
