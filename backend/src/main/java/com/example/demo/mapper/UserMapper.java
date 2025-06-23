package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.model.User;
import com.example.demo.records.UserDTO;

@Mapper(componentModel="spring")
public interface  UserMapper {
    
    UserDTO userDTO( User user) ; 

    @Mapping(target="id" , ignore=true)
    @Mapping(target="password" , ignore=true)
    @Mapping(target="orders" , ignore=true)
    @Mapping(target="addresses" , ignore=true)
    @Mapping(target="productsAddedToCart" , ignore=true)
    @Mapping(target="role" , ignore=true)
    User user(UserDTO userDTO) ; 
}
