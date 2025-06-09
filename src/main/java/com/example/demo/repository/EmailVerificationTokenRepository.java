package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.EmailVerificationToken;

public interface  EmailVerificationTokenRepository extends  JpaRepository<EmailVerificationToken, Long > {
    
    EmailVerificationToken  findByCode(String code) ;
}
