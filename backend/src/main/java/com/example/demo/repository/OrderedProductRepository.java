package com.example.demo.repository;



import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.OrderedProduct;

public interface  OrderedProductRepository extends JpaRepository<OrderedProduct, UUID> {   
    
}