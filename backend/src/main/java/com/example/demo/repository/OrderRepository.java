package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;

@Repository
public interface  OrderRepository extends   JpaRepository< Order , String > {    
      public List<Order> findByUserUsername( String username ) ; 
} 
