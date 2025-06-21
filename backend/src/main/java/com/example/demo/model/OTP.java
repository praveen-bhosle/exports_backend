package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ; 
    private Long code ;
    public OTP(Long code) { 
        this.code = code ;
    }
}
