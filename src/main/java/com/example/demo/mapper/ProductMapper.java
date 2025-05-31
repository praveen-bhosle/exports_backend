package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.model.Product;
import com.example.demo.records.ProductDTO;

@Mapper(componentModel="spring")
public interface ProductMapper { 
  ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class) ;
  ProductDTO toDTO(Product product) ; 
  Product toProduct(ProductDTO productDTO  ) ;
} 