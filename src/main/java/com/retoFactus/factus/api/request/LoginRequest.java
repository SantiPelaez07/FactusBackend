package com.retoFactus.factus.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String userName;
    private String password;
}
