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
import com.retoFactus.factus.api.response.ProductResponse;
import com.retoFactus.factus.api.response.secundaryResponse.InvoiceProductAditionalResponse;
import com.retoFactus.factus.api.response.secundaryResponse.UserSecundaryResponse;
import com.retoFactus.factus.domain.entities.Invoice;
import com.retoFactus.factus.domain.entities.InvoiceProduct;
import com.retoFactus.factus.domain.entities.Product;
import com.retoFactus.factus.domain.entities.User;
import com.retoFactus.factus.domain.repositories.InvoiceProductRepository;
import com.retoFactus.factus.domain.repositories.InvoiceRepository;
import com.retoFactus.factus.domain.repositories.UserRepository;
import com.retoFactus.factus.infrastructure.abstract_service.IInvoiceService;
import com.retoFactus.factus.utils.SortType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceService implements IInvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceProductRepository invoiceProductRepository;
    private final UserRepository userRepository;


    @Override
    public InvoiceResponse create(InvoiceRequest request) {
        Invoice invoice = this.requestToEntity(request);
        invoice.setCreatedAt(LocalDateTime.now());
        this.invoiceRepository.save(invoice);
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
        List<InvoiceProduct> invoiceProduct = this.getIdInvoiceProduct(request.getIdinvoiceProducts());
        Invoice invoice = Invoice.builder()
        .createdAt(LocalDateTime.now())
        .invoiceUrl(request.getInvoiceUrl())
        .invoiceProducts(invoiceProduct)
        .totalPrice(this.getTotal(request.getIdinvoiceProducts()))
        .user(this.getByIdUser(request.getIdUser())).build();
        for(InvoiceProduct ip : invoiceProduct){
            ip.setInvoice(invoice);
        }
        invoice.setInvoiceProducts(invoiceProduct);
        return invoice;
    }

    private double getTotal(List<Long> ids){
        double total = 0;
        for(Long id : ids){
            InvoiceProduct invoiceProducts = this.invoiceProductRepository.findById(id).orElseThrow(() -> new IllegalStateException("No se pudo calcular el total de la factura."));
            Product product = invoiceProducts.getProduct();
            if(product == null) throw new IllegalStateException("El producto asociado al InvoiceProduct con ID " + id + " es nulo.");
            total += invoiceProducts.getQuantity() * product.getPrice();
        }

        return total;
    }


    private InvoiceResponse entityToResponse(Invoice entity){
        List<InvoiceProductAditionalResponse> invoiceProduct = new ArrayList<>();
        if(entity.getInvoiceProducts() == null){
            entity.setInvoiceProducts(null);
        }else {
            invoiceProduct = entity.getInvoiceProducts().stream().map(
                invoiceProductList -> {
                    InvoiceProductAditionalResponse invoicep = new InvoiceProductAditionalResponse();
                    BeanUtils.copyProperties(invoiceProductList, invoicep);
                    invoicep.setPrice(invoiceProductList.getProduct().getPrice());
                    invoicep.setProduct(this.convertProductResponse(invoiceProductList.getProduct()));
                    
                    return invoicep;
        }).collect(Collectors.toList());

        }
        return InvoiceResponse.builder()
        .idInvoice(entity.getIdInvoice())
        .createdAt(entity.getCreatedAt())
        .invoiceUrl(entity.getInvoiceUrl())
        .totalPrice(entity.getTotalPrice())
        .invoiceProductList(invoiceProduct)
        .user(this.convertResponseUser(entity.getUser())).build();
    }

    private ProductResponse convertProductResponse(Product product){
        ProductResponse productR = new ProductResponse();
        productR.setIdProduct(product.getIdProduct());
        productR.setNameProduct(product.getNameProduct());
        productR.setPrice(product.getPrice());
        return productR;
    }

    private Invoice getId(Long id){
        Invoice invoice = this.invoiceRepository.findById(id).orElseThrow();
        return invoice;
    }
    
    private List<InvoiceProduct> getIdInvoiceProduct(List<Long> ids){
        List<InvoiceProduct> invoiceProduct = this.invoiceProductRepository.findAllById(ids);
        if(invoiceProduct.isEmpty()) throw new RuntimeException("No se encontraron InvoiceProduct con los IDS: " + ids);
        return invoiceProduct;
    }

    private User getByIdUser(Long id){
        User user = this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("El usuario con ID " + id + " no existe."));
        return user;
    }

    private UserSecundaryResponse convertResponseUser(User user){
        return UserSecundaryResponse.builder()
                .idUser(user.getIdUser())
                .nameUser(user.getNameUser())
                .lastNameUser(user.getLastNameUser())
                .dni(user.getDni())
                .departament(user.getDepartament())
                .address(user.getAddress())
                .mail(user.getMail())
                .phone(user.getPhone()).build();
    }

}
