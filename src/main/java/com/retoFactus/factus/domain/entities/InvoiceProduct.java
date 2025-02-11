package com.retoFactus.factus.domain.entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "invoiceProduct")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInvoiceProduct;
    @Positive
    @Column(nullable = false)
    private int quantity;

    //Relation with invoice
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idInvoice",  referencedColumnName = "idInvoice")
    private Invoice invoice;

    //Relation with product
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProduct",  referencedColumnName = "idProduct")
    private Product product;

}
