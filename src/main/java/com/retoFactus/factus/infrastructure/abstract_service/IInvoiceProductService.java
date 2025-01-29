package com.retoFactus.factus.infrastructure.abstract_service;

import com.retoFactus.factus.api.request.InvoiceProductRequest;
import com.retoFactus.factus.api.response.InvoiceProductResponse;

public interface IInvoiceProductService extends CrudDefault<InvoiceProductRequest, InvoiceProductResponse, Long> {
    public final String FIELD_BY_SORT = "idInvoiceProduct";
}
