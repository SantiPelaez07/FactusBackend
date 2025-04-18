package com.retoFactus.factus.api.response;

import java.time.LocalDateTime;
import java.util.List;

import com.retoFactus.factus.api.response.secundaryResponse.InvoiceProductAditionalResponse;
import com.retoFactus.factus.api.response.secundaryResponse.UserSecundaryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    private Long idInvoice;
    private LocalDateTime createdAt;
    private String invoiceUrl;
    private double totalPrice;
    private List<InvoiceProductAditionalResponse> invoiceProductList;
    private UserSecundaryResponse user;
}
