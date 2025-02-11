package com.retoFactus.factus.api.request;


import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {
    private String invoiceUrl;
    private Long idUser;
    private List<Long> idinvoiceProducts = new ArrayList<>();
}
