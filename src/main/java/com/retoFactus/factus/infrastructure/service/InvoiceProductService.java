package com.retoFactus.factus.infrastructure.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.retoFactus.factus.api.request.InvoiceProductRequest;
import com.retoFactus.factus.api.response.InvoiceProductResponse;
import com.retoFactus.factus.api.response.ProductResponse;
import com.retoFactus.factus.api.response.secundaryResponse.InvoiceSecundaryResponse;
import com.retoFactus.factus.domain.entities.Invoice;
import com.retoFactus.factus.domain.entities.InvoiceProduct;
import com.retoFactus.factus.domain.entities.Product;
import com.retoFactus.factus.domain.repositories.InvoiceProductRepository;
import com.retoFactus.factus.domain.repositories.ProductRepository;
import com.retoFactus.factus.infrastructure.abstract_service.IInvoiceProductService;
import com.retoFactus.factus.utils.SortType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceProductService implements IInvoiceProductService {

    private final InvoiceProductRepository repository;
    private final ProductRepository productRepository;

    @Override
    public InvoiceProductResponse create(InvoiceProductRequest request) {
        InvoiceProduct invoiceProduct = this.repository.save(requestToEntity(request));
        return this.entityToResponse(invoiceProduct);
    }

    @Override
    public InvoiceProductResponse getById(Long id) {
        InvoiceProduct invoiceProduct = this.getIdInvoiceProduct(id);
        return this.entityToResponse(invoiceProduct);
    }

    @Override
    public InvoiceProductResponse update(InvoiceProductRequest request, Long id) {
        InvoiceProduct invoiceProduct = this.getIdInvoiceProduct(id);
        InvoiceProduct update = new InvoiceProduct();
        if (invoiceProduct != null) {
            update = this.requestToEntity(request);
            update.setIdInvoiceProduct(id);
            
        }
        return this.entityToResponse(this.repository.save(update));
    }

    @Override
    public void delete(Long id) {
        InvoiceProduct invoiceProduct = this.getIdInvoiceProduct(id);
        this.repository.delete(invoiceProduct);
    }

    @Override
    public Page<InvoiceProductResponse> getAll(int page, int size, SortType sort) {
        if(size < 0) size = 0;
        PageRequest pagination = null;
        switch(sort){
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.repository.findAll(pagination).map(this::entityToResponse);
    }

    private InvoiceProduct requestToEntity(InvoiceProductRequest request){
        return InvoiceProduct.builder()
        .quantity(request.getQuantity())
        .product(this.getProductById(request.getIdProducts())).build();
    }

    private InvoiceProductResponse entityToResponse(InvoiceProduct entity){
        if(entity.getInvoice() == null){
            return InvoiceProductResponse.builder()
        .idInvoiceProduct(entity.getIdInvoiceProduct())
        .quantity(entity.getQuantity())
        .product(convertProduct(entity.getProduct())).build();
        }else {
            return InvoiceProductResponse.builder()
            .idInvoiceProduct(entity.getIdInvoiceProduct())
            .quantity(entity.getQuantity())
            .invoice(this.convertInvoice(entity.getInvoice()))
            .product(convertProduct(entity.getProduct())).build();
        }
    }

    private ProductResponse convertProduct(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setIdProduct(product.getIdProduct());
        productResponse.setNameProduct(product.getNameProduct());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }

    private InvoiceSecundaryResponse convertInvoice(Invoice invoice){
        InvoiceSecundaryResponse invoiceSecundaryResponse = new InvoiceSecundaryResponse();
        invoiceSecundaryResponse.setCreatedAt(invoice.getCreatedAt());
        invoiceSecundaryResponse.setIdInvoice(invoice.getIdInvoice());
        invoiceSecundaryResponse.setInvoiceUrl(invoice.getInvoiceUrl());
        invoiceSecundaryResponse.setTotalPrice(invoice.getTotalPrice());
        return invoiceSecundaryResponse;
    }

    private InvoiceProduct getIdInvoiceProduct(Long id){
        InvoiceProduct invoiceProduct = this.repository.findById(id).orElseThrow();
        return invoiceProduct;
    }

    private Product getProductById(Long id){
        Product product = this.productRepository.findById(id).orElseThrow();
        return product;
    }

    
}
