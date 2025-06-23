package com.example.demo.records;

import jakarta.validation.constraints.NotNull;

public record TransactionDTO ( @NotNull String razorpay_payment_id , @NotNull String razorpay_order_id ,@NotNull String razorpay_signatue ,  Long id , String username ) {}
