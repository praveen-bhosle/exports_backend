package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Entity
@Table(name="Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {   
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ; 

    private String password ;
    private String email ; 
    private String phone ;

    @OneToMany(mappedBy="user")
    private List<Order> orders ; 


    @OneToMany(mappedBy="user")
    private List<AddedToCartProduct>  productsAddedToCart ;  

     
    public  User( String email ,  String password)  { 
        this.password = password ; 
        this.email = email ;
    }

}