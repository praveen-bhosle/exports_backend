package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="Transactions")  
public class Transaction {  

    @Id
    @GeneratedValue ( strategy=  GenerationType.IDENTITY ) 
    private Long id ;

    private Long productCost ;
    private Long tax ; 
    private Long totalCost ; 
    private Date createdAT  ;
       
}