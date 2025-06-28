package com.example.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.TransactionMapper;
import com.example.demo.model.Transaction;
import com.example.demo.records.TransactionDTO;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.utils.GetUsername;

@Service
public class TransactionService {

    @Autowired 
    private TransactionMapper transactionMapper ; 

    @Autowired 
    private TransactionRepository transactionRepository ; 

    public Transaction  createTransaction(TransactionDTO transactionDTO ) {  
       Transaction transaction =   transactionMapper.toTransaction(transactionDTO) ; 
       String username = GetUsername.getUsername() ; 
       transaction.setUsername(username);
       transactionRepository.save(transaction) ; 
       return transaction ; 
    } 
    
    public boolean verifyTransaction( TransactionDTO transactionDTO  ) {  
        UUID id = transactionDTO.id() ; 
        if(id == null ) return false ;  
        Transaction transaction = transactionRepository.findById(id).orElse(null)  ;
        if(transaction == null )  return false ; 
        return transaction.getUsername().equals(GetUsername.getUsername()) ; 
    }
}