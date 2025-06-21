package com.example.demo.mapper ;

import org.mapstruct.Mapper;

import com.example.demo.model.CartProduct;
import com.example.demo.records.CartProductDTO;

@Mapper(componentModel="spring")
public interface CartProductMapper  { 
   
    CartProductDTO toDTO(  CartProduct cartProuduct ) ;
}