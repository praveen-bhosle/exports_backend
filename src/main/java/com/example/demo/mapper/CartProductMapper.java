package com.example.demo.mapper ;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.demo.model.CartProduct;
import com.example.demo.records.CartProductDTO;



@Mapper(componentModel="spring")
public interface CartProductMapper  { 
    CartProductMapper INSTANCE = Mappers.getMapper( CartProductMapper.class)     ;  
    CartProductDTO toDTO(  CartProduct cartProuduct ) ;
}