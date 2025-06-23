package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.model.Transaction;
import com.example.demo.records.TransactionDTO;

@Mapper( componentModel="spring")
public interface  TransactionMapper {
    
    TransactionDTO toDTO(Transaction transcation);  

    @Mapping( target = "id" , ignore = true  )
    @Mapping(target="username" , ignore= true )
    Transaction    toTransaction( TransactionDTO transactionDTO ) ;

}
