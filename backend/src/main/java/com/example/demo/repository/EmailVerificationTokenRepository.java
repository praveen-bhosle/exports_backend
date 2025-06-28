package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.EmailVerificationToken;

public interface  EmailVerificationTokenRepository extends  JpaRepository<EmailVerificationToken, UUID > {
    
    EmailVerificationToken  findByCode(String code) ;
}
