package com.retoFactus.factus.api.response.secundaryResponse;

import com.retoFactus.factus.api.response.ProductResponse;
import com.retoFactus.factus.domain.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceProductAditionalResponse {
private Long idInvoiceProduct;
    private int quantity;
    private double price;
    private ProductResponse product;
}
