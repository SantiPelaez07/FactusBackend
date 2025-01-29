package com.retoFactus.factus.infrastructure.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.retoFactus.factus.api.request.InvoiceRequest;
import com.retoFactus.factus.api.response.InvoiceResponse;
import com.retoFactus.factus.api.response.secundaryResponse.InvoiceProductSecundaryResponse;
import com.retoFactus.factus.domain.entities.Invoice;
import com.retoFactus.factus.domain.entities.InvoiceProduct;
import com.retoFactus.factus.domain.entities.Product;
import com.retoFactus.factus.domain.repositories.InvoiceProductRepository;
import com.retoFactus.factus.domain.repositories.InvoiceRepository;
import com.retoFactus.factus.domain.repositories.ProductRepository;
import com.retoFactus.factus.infrastructure.abstract_service.IInvoiceService;
import com.retoFactus.factus.utils.SortType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceService implements IInvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final InvoiceProductRepository invoiceProductRepository;
    @Override
    public InvoiceResponse create(InvoiceRequest request) {
        Invoice invoice = this.invoiceRepository.save(this.requestToEntity(request));
        return this.entityToResponse(invoice);
    }


    @Override
    public InvoiceResponse getById(Long id) {
        Invoice invoice = this.getId(id);
        return this.entityToResponse(invoice);
    }


    @Override
    public InvoiceResponse update(InvoiceRequest request, Long id) {
        Invoice invoice = this.getId(id);
        Invoice update = new Invoice();
        if (invoice != null) {
            update = this.requestToEntity(request);
            update.setIdInvoice(id);
        }
        return this.entityToResponse(update);
    }


    @Override
    public void delete(Long id) {
        Invoice invoice = this.getId(id);
        this.invoiceRepository.delete(invoice);
    }


    @Override
    public Page<InvoiceResponse> getAll(int page, int size, SortType sort) {
        if(size < 0) size = 0;

        PageRequest pagination = null;
        switch (sort) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.invoiceRepository.findAll(pagination).map(this::entityToResponse);
    }


    private Invoice requestToEntity(InvoiceRequest request){
        return Invoice.builder()
        .createdAt(LocalDateTime.now())
        .invoiceUrl(request.getInvoiceUrl())
        .totalPrice(this.getTotal(request.getIdProduct())). build();
    }

    private double getTotal(Long id){
        Product product = this.productRepository.findById(id).orElseThrow();
        InvoiceProduct invoiceProduct = this.invoiceProductRepository.findById(id).orElseThrow();
        double total = product.getPrice() * invoiceProduct.getQuantity();
        return total;
    }

    private InvoiceResponse entityToResponse(Invoice entity){
        List<InvoiceProductSecundaryResponse> invoiceProduct = new ArrayList<>();
        if(entity.getInvoiceProductList() == null){
            entity.setInvoiceProductList(null);
        }else {
            invoiceProduct = entity.getInvoiceProductList().stream().map(
                invoiceProductList -> {
                    InvoiceProductSecundaryResponse invoicep = new InvoiceProductSecundaryResponse();
                    BeanUtils.copyProperties(invoiceProductList, invoicep);
                    return invoicep;
                }).collect(Collectors.toList());
        }
        return InvoiceResponse.builder()
        .createdAt(entity.getCreatedAt())
        .invoiceUrl(entity.getInvoiceUrl())
        .totalPrice(entity.getTotalPrice())
        .invoiceProductList(invoiceProduct)
        .user(entity.getUser()).build();
    }

    private Invoice getId(Long id){
        Invoice invoice = this.invoiceRepository.findById(id).orElseThrow();
        return invoice;
    }
    

}
