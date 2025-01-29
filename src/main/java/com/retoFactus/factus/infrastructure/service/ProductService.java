package com.retoFactus.factus.infrastructure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.retoFactus.factus.api.request.ProductRequest;
import com.retoFactus.factus.api.response.InvoiceProductResponse;
import com.retoFactus.factus.api.response.ProductResponse;
import com.retoFactus.factus.domain.entities.InvoiceProduct;
import com.retoFactus.factus.domain.entities.Product;
import com.retoFactus.factus.domain.repositories.ProductRepository;
import com.retoFactus.factus.infrastructure.abstract_service.IProductService;
import com.retoFactus.factus.utils.SortType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    
    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = this.productRepository.save(this.requestToEntity((request)));
        return this.entityToResponse(product);
    }

    @Override
    public ProductResponse getById(Long id) {
        Product product = this.productRepository.findById(id).orElseThrow();
        return this.entityToResponse(product);
    }

    @Override
    public ProductResponse update(ProductRequest request, Long id) {
        Product product = this.getId(id);
        Product update = new Product();
        if (product != null) {
            update = this.requestToEntity(request);
            update.setIdProduct(id);
        }
        return this.entityToResponse(this.productRepository.save(update));

    }

    @Override
    public void delete(Long id) {
        Product product = this.getId(id);
        this.productRepository.delete(product);
    }

    @Override
    public Page<ProductResponse> getAll(int page, int size, SortType sort) {
        if(size < 0) size = 0;
        PageRequest pagination = null;
        switch (sort) {
            case NONE -> pagination = PageRequest.of(page, size);
            case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        }
        return this.productRepository.findAll(pagination).map(this::entityToResponse);
    }

    public Product requestToEntity(ProductRequest request){
        List<InvoiceProduct> invoiceList = new ArrayList<>();
        return Product.builder()
        .nameProduct(request.getNameProduct())
        .price(request.getPrice())
        .invoiceProductList(invoiceList).build();
    }

    public ProductResponse entityToResponse(Product entity){
        List<InvoiceProductResponse> invoiceList = new ArrayList<>();
        if(entity.getInvoiceProductList() == null) {
            entity.setInvoiceProductList(null);
        }else {
            invoiceList = entity.getInvoiceProductList().stream().map(
                invoiceProductList -> {
                    InvoiceProductResponse invoiceProduct = new InvoiceProductResponse();
                    BeanUtils.copyProperties(invoiceProductList, invoiceProduct);
                    return invoiceProduct;
                }).collect(Collectors.toList());
        }
        return ProductResponse.builder()
        .nameProduct(entity.getNameProduct())
        .price(entity.getPrice())
        .invoiceProductList(invoiceList).build();
    }

    private Product getId(Long id){
        Product product = this.productRepository.findById(id).orElseThrow();
        return product;
    }

}
