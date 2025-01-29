package com.retoFactus.factus.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retoFactus.factus.domain.entities.InvoiceProduct;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

}
