package com.retoFactus.factus.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String nameUser;
    private String lastNameUser;
    private String dni;
    private String departament;
    private String address;
    private String mail;
    private String phone;
}
