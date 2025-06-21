package com.example.demo.mapper;

import org.mapstruct.Mapper;

import com.example.demo.model.Product;
import com.example.demo.records.ProductDTO;

@Mapper(componentModel="spring")
public interface ProductMapper { 
  ProductDTO toDTO(Product product) ; 
} 