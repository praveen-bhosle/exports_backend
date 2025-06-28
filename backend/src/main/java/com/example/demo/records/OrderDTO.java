package com.example.demo.records;

import java.time.LocalDateTime;

import com.example.demo.enums.OrderStatusEnum;

public record OrderDTO ( String id ,  Long totalCost ,  OrderStatusEnum orderStatus  , LocalDateTime  createdAt   ){} 