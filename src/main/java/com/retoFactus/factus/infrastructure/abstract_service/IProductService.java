package com.retoFactus.factus.infrastructure.abstract_service;

import com.retoFactus.factus.api.request.ProductRequest;
import com.retoFactus.factus.api.response.ProductResponse;

public interface IProductService extends CrudDefault<ProductRequest, ProductResponse, Long>{
    public final String FIELD_BY_SORT = "nameProduct";
}
