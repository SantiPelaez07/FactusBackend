package com.retoFactus.factus.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private String userName;
    private String address;
    private String dni;
    private String mail;
    private String phone;
}
