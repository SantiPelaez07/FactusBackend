package com.retoFactus.factus.infrastructure.abstract_service;

import com.retoFactus.factus.api.request.InvoiceRequest;
import com.retoFactus.factus.api.response.InvoiceResponse;

public interface IInvoiceService extends CrudDefault<InvoiceRequest, InvoiceResponse, Long> {
    public final String FIELD_BY_SORT = "idInvoice";
}
