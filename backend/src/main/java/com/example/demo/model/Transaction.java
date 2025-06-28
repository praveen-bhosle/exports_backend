package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue
    private UUID id ;   
    private String razorpay_payment_id ;
    private String razorpay_order_id ; 
    private String razorpay_signatue ; 
    private String username ; 
    @CreationTimestamp
    private LocalDateTime createdAt ; 
}
