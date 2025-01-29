package com.retoFactus.factus.api.response.secundaryResponse;

import java.time.LocalDateTime;
import java.util.List;

import com.retoFactus.factus.api.response.InvoiceResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceSecundaryResponse {
    private Long idInvoice;
    private LocalDateTime createdAt;
    private String invoiceUrl;
    private String totalPrice;
    private List<InvoiceResponse> invoiceProductList;
}
