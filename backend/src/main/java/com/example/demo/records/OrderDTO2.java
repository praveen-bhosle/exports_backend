package com.example.demo.records;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.model.OrderedProduct;

public record OrderDTO2 ( String id ,  List<OrderedProduct> orderedProducts , Long totalCost ,  OrderStatusEnum orderStatus  , LocalDateTime  createdAt   ){} 