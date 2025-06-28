package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.demo.enums.RoleEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 
import lombok.ToString;

@Entity
@Table(name="Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User    {   
 

    @Id
    @GeneratedValue
    private UUID id ; 

    @Column(unique=true) 
    private String username ; 

    private String password ; 
    @Column(unique=true)
    private String email ; 
 
    @Column(unique=true) 
    private String phone ; 


    @OneToMany(mappedBy="user" , fetch=FetchType.LAZY)
    @ToString.Exclude
    private List<Order> orders ; 

    @OneToMany(mappedBy="user" , fetch=FetchType.LAZY )
    @ToString.Exclude
    private List<CartProduct>  productsAddedToCart ;   

    @Enumerated(EnumType.STRING)
    private RoleEnum role ; 
     
    public  User( String username ,  String password ,  RoleEnum role   )  { 
        this.password = password ; 
        this.username = username ;
        this.role = role  ;
    } 

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="profile_id")
    private Profile profile ; 

    @OneToMany( mappedBy="user" ,  cascade=CascadeType.ALL,fetch=FetchType.LAZY , orphanRemoval=true )
    @ToString.Exclude
    private List<Address> addresses ;   

    @CreationTimestamp
    private LocalDateTime createdAt ; 

    @UpdateTimestamp
    private LocalDateTime updatedAt; 

}