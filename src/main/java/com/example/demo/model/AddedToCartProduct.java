package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="AddedToCartProducts")
@Data
public class AddedToCartProduct { 

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ;
    
    @ManyToOne
    @JoinColumn(name="username" , referencedColumnName="username")  
    private User user ;

    @ManyToOne
    @JoinColumn(name="productId" , referencedColumnName="id")
    private Product product    ;  

}