package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="address")
public class Address { 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ; 

    private String fullName     ; 
    private String mobileNumber ;
    private String pincode      ;
    private String addr1        ; 
    private String addr2        ;
    private String landmark     ; 
    private String city         ; 
    private String state        ;
    private String country      ; 
    private Boolean isDefault   ; 

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id") 
    private User user           ;
}