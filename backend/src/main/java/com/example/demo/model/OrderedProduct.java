package com.example.demo.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="OrderedProducts")
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProduct {  

    @Id
    @GeneratedValue
    private UUID id ; 

    @ManyToOne
    @JoinColumn(name="productId" , referencedColumnName= "id") 
    private Product product ;

    @ManyToOne
    @JoinColumn(name="orderId" , referencedColumnName="id") 
    @JsonIgnore
    private Order order ;

    private Long quantity; 

    public OrderedProduct( Product product ,  Order order   ,  Long quanity ) { 
        this.product =  product ;
        this.order = order ; 
        this.quantity = quanity ; 
    }
    
}