package com.retoFactus.factus.api.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceProductRequest {
    private int quantity;
    private Long idProduct;
    private Long idInvoice;
}
