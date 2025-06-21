package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ; 

    @ManyToOne
    @JoinColumn(name="productId" , referencedColumnName= "id") 
    private Product product ;

    @ManyToOne
    @JoinColumn(name="orderId" , referencedColumnName="id") 
    private Order order ;

    private Long quantity; 
    
}