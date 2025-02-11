package com.retoFactus.factus.api.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retoFactus.factus.api.request.InvoiceRequest;
import com.retoFactus.factus.api.response.InvoiceResponse;
import com.retoFactus.factus.infrastructure.service.InvoiceService;
import com.retoFactus.factus.utils.SortType;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(path = "/invoice")
@AllArgsConstructor
public class InvoiceController {

    @Autowired
    private final InvoiceService service;

    @PostMapping
    public ResponseEntity<InvoiceResponse> create(@RequestBody InvoiceRequest request){
        return ResponseEntity.ok(this.service.create(request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<InvoiceResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(this.service.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<InvoiceResponse>> getAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestHeader(required = false) SortType sortType
    ){
        if(Objects.isNull(sortType)) sortType = SortType.NONE;
        return ResponseEntity.ok(this.service.getAll(page - 1, size, sortType));
    }
    
    @PutMapping(path = "/{id}")
    public ResponseEntity<InvoiceResponse> update(@Validated @PathVariable Long id, @RequestBody InvoiceRequest request){
        return ResponseEntity.ok(this.service.update(request, id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
