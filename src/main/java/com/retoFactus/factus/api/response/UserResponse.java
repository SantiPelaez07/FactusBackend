package com.retoFactus.factus.api.response;




import java.util.List;

import com.retoFactus.factus.api.response.secundaryResponse.InvoiceSecundaryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long idUser;
    private String nameUser;
    private String lastNameUser;
    private String dni;
    private String departament;
    private String municipality;
    private String address;
    private String mail;
    private String phone;
    private List<InvoiceSecundaryResponse> invoiceSecundaryResponse;
}
