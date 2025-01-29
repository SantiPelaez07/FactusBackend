package com.retoFactus.factus.api.response.secundaryResponse;

import com.retoFactus.factus.domain.entities.Invoice;
import com.retoFactus.factus.domain.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceProductSecundaryResponse {

    private Long idInvoiceProduct;
    private int quantity;
    private String price;
    private Invoice invoice;
    private Product product;
}
