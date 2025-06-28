package com.example.demo.mapper ;


import org.mapstruct.Mapper;

import com.example.demo.model.Order;
import com.example.demo.records.OrderDTO2;



@Mapper(componentModel="spring")
public interface Order2Mapper {   
  OrderDTO2  toDTO(Order order ) ;
}