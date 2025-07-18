package com.example.demo.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="AddedToCartProducts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProduct { 

    @Id
    @GeneratedValue
    private UUID id ;
    
    @ManyToOne
    @JoinColumn(name="username" , referencedColumnName="username")  
    private User user ;

    @ManyToOne
    @JoinColumn(name="productId" , referencedColumnName="id")
    private Product product    ;   

    private Long quantity ;

    public  CartProduct( User user ,  Product product ) { 
        this.user = user ; 
        this.product = product ;
        this.quantity = 500L ; 
    }  

}