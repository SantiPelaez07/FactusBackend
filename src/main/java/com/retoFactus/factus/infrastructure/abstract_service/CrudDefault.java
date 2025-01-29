package com.retoFactus.factus.infrastructure.abstract_service;

import org.springframework.data.domain.Page;

import com.retoFactus.factus.utils.SortType;

public interface CrudDefault <RQ, RS, ID> {
    public RS create(RQ request);
    public RS getById(ID id);
    public RS update(RQ request, ID id);
    public void delete(ID id);
    public Page<RS> getAll(int page, int size, SortType sort);
}
