package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CartProduct; 

public interface  CartProductRepository extends  JpaRepository< CartProduct, UUID>  {  
    List<CartProduct> findByUserUsername( String username ) ;   
}
