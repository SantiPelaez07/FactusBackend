package com.retoFactus.factus.api.response.secundaryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSecundaryResponse {
    private Long idUser;
    private String nameUser;
    private String lastNameUser;
    private String dni;
    private String departament;
    private String address;
    private String mail;
    private String phone;
}
