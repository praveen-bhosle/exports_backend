package com.example.demo.model; 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Products") 
@Data
@NoArgsConstructor
public class Product { 

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Long id ; 

    private Long stock ;
    private Long cost ; 

    @OneToMany(mappedBy="product")
    private OrderedProduct orderedProduct ; 

    @OneToMany(mappedBy="product") 
    private AddedToCartProduct addedToCartProduct ;

}