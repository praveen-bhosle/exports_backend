package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CartProduct; 

public interface  CartProductRepository extends  JpaRepository< CartProduct, Long>  {  
    List<CartProduct> findByUserUsername( String username ) ;   
}
