package com.example.demo.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue
    private UUID id ; 

    @NotNull
    private String fullName     ; 
    @NotNull
    private String mobileNumber ;
    @NotNull
    private String pincode      ;
    private String addr1        ; 
    private String addr2        ;
    private String landmark     ; 
    @NotNull
    private String city         ; 
    @NotNull
    private String state        ;
    @NotNull
    private String country      ; 
    @NotNull
    private Boolean isDefault   ; 

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_username" ,referencedColumnName="username") 
    private User user           ;

}