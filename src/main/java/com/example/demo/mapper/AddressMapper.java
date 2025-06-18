package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.model.Address;
import com.example.demo.records.AddressDTO;

@Mapper(componentModel="spring")
public interface  AddressMapper  {
    AddressDTO  toAddressDTO(Address address) ; 

  //  @Mapping(target="id" ,  ignore= true   ) 
    @Mapping(target="user" ,  ignore= true ) 
    Address   toAddress(AddressDTO address ) ;  
}
