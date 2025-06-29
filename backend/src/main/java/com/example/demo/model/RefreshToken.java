package com.example.demo.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken { 

    @Id
    @GeneratedValue 
    UUID id ; 

    @Column(nullable=false,unique=true)
    private String token  ; 

    @ManyToOne(fetch=FetchType.LAZY) 
    @JoinColumn(name="user_id",nullable=false) 
    private User user ; 


    @Column(nullable=false)
    private Instant expiryDate ; 

    @Column(nullable=false) 
    private boolean revoked = false ; 
}
