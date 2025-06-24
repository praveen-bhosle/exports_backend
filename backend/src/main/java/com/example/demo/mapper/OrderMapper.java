package com.example.demo.mapper;

import org.mapstruct.Mapper;

import com.example.demo.model.Order;
import com.example.demo.records.OrderDTO;

@Mapper(componentModel="spring")
public interface  OrderMapper {
     OrderDTO  toDTO ( Order order)  ; 
}
