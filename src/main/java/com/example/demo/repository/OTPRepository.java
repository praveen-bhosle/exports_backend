package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.OTP;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {}