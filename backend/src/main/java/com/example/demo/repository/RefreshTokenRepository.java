package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.RefreshToken;

public interface  RefreshTokenRepository extends  JpaRepository< RefreshToken , UUID >  {   
    RefreshToken findByToken(String token) ;  
}