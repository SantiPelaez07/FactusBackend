package com.retoFactus.factus.infrastructure.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.retoFactus.factus.api.request.ProductRequest;
import com.retoFactus.factus.api.response.ProductResponse;
import com.retoFactus.factus.domain.entities.InvoiceProduct;
import com.retoFactus.factus.domain.entities.Product;
import com.retoFactus.factus.domain.repositories.ProductRepository;
import com.retoFactus.factus.infrastructure.abstract_service.IProductService;
import com.retoFactus.factus.infrastructure.cloudinary.CloudinaryAdapter;
import com.retoFactus.factus.utils.SortType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    
    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = this.requestToEntity(request);
        CloudinaryAdapter cloudinary = new CloudinaryAdapter();
        String urlImage = cloudinary.uploadImage(request.getUrlImage());
        product.setUrlImage(urlImage);
        return this.entityToResponse(this.productRepository.save(product));
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
            CloudinaryAdapter cloudinary = new CloudinaryAdapter();
            String urlImage = cloudinary.uploadImage(request.getUrlImage());
            update.setUrlImage(urlImage);
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
        .urlImage(request.getUrlImage())
        .nameProduct(request.getNameProduct())
        .price(request.getPrice())
        .invoiceProductsList(invoiceList).build();
    }

    public ProductResponse entityToResponse(Product entity){
        // List<InvoiceProductResponse> invoiceList = new ArrayList<>();
        // if(entity.getInvoiceProductsList() == null) {
        //     entity.setInvoiceProductsList(null);
        // }else {
        //     invoiceList = entity.getInvoiceProductsList().stream().map(
        //         invoiceProductList -> {
        //             InvoiceProductResponse invoiceProduct = new InvoiceProductResponse();
        //             BeanUtils.copyProperties(invoiceProductList, invoiceProduct);
        //             return invoiceProduct;
        //         }).collect(Collectors.toList());
        // }
        return ProductResponse.builder()
        .idProduct(entity.getIdProduct())
        .urlImage(entity.getUrlImage())
        .nameProduct(entity.getNameProduct())
        .price(entity.getPrice()).build();
    }

    private Product getId(Long id){
        Product product = this.productRepository.findById(id).orElseThrow();
        return product;
    }

}
