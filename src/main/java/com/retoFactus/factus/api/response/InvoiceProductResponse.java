package com.retoFactus.factus.api.response;


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
public class InvoiceProductResponse {
    private Long idInvoiceProduct;
    private int quantity;
    private Invoice invoice;
    private Product product;
}
