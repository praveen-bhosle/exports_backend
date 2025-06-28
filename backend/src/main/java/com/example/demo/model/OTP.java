package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.enums.OTPStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="OTPs")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class OTP { 
    @Id
    @GeneratedValue
    private UUID id ; 
    private Long code ;
    private OTPStatus status  = OTPStatus.SENT ; 
    private String username ; 
    @CreationTimestamp
    private LocalDateTime createdAt ; 
    private int retriesLeft = 3 ;  
    public OTP(Long code ,  String username ) { 
        this.code = code ;
        this.username = username ;  
    }
    public void  decreaseRetries() { 
        if(this.retriesLeft>0) this.retriesLeft = this.retriesLeft -1 ; 
    }
}
