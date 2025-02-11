package com.retoFactus.factus.api.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long idProduct;
    private String nameProduct;
    private double price;
}
