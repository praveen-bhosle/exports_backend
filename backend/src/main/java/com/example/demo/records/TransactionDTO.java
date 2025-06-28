package com.example.demo.records;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record TransactionDTO ( @NotNull String razorpay_payment_id , @NotNull String razorpay_order_id ,@NotNull String razorpay_signatue ,  UUID id , String username  ,  LocalDateTime createdAt ) {}