package com.example.demo.model; 

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Products") 
@Data
@NoArgsConstructor
public class Product { 
    @Id
    @GeneratedValue
    private UUID id ; 
    private Long cost ; 
    @ToString.Exclude
    @OneToMany(mappedBy="product")
    @JsonIgnore
    private  List<OrderedProduct> orderedProducts ; 
    @ToString.Exclude
    @OneToMany(mappedBy="product") 
    @JsonIgnore
    private List<CartProduct> addedToCartProducts ;
    private String sizeA ; 
    private String sizeB ; 
    private String quality ;
    @ManyToMany
    @JoinTable( name="product_images", joinColumns=@JoinColumn( name="productId"),inverseJoinColumns=@JoinColumn(name="imageName")  )
    @ToString.Exclude
    private List<Image> images  ; 
}