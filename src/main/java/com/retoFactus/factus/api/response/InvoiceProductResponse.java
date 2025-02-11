package com.retoFactus.factus.api.response;


import com.retoFactus.factus.api.response.secundaryResponse.InvoiceSecundaryResponse;

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
    private InvoiceSecundaryResponse invoice;
    private ProductResponse product;
}
