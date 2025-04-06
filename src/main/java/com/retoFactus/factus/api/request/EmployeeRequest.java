package com.retoFactus.factus.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private String userName;
    private String password;
    private String address;
    private String dni;
    private String mail;
    private String phone;
}
