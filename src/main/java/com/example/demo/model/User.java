package com.example.demo.model;


import java.util.List;

import com.example.demo.enums.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
public class User    {   
 

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY ) 
    private Long id ; 

    @Column(unique=true) 
    private String username ; 

    private String password ; 
    private String email ; 
 
    @OneToMany(mappedBy="user" , fetch=FetchType.LAZY)
    private List<Order> orders ; 

    @OneToMany(mappedBy="user" , fetch=FetchType.LAZY )
    private List<CartProduct>  productsAddedToCart ;   

    @Enumerated(EnumType.STRING)
    private RoleEnum role ; 
     
    public  User( String username ,  String password ,  RoleEnum role   )  { 
        this.password = password ; 
        this.username = username ;
        this.role = role  ;
    }

}