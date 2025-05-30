package com.example.demo.model; 

import java.util.List;

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
    private  List<OrderedProduct> orderedProducts ; 
    @OneToMany(mappedBy="product") 
    private List<CartProduct> addedToCartProducts ;
    private String sizeA ; 
    private String sizeB ; 
}