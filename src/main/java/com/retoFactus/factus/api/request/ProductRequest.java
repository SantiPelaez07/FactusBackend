package com.retoFactus.factus.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String urlImage;
    private String nameProduct;
    private double price;
}
