package com.example.demo.records;

import java.util.List;

import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.model.OrderedProduct;

public record OrderDTO ( String id ,  List<OrderedProduct> orderedProducts , Long totalCost ,  OrderStatusEnum orderStatus)  {} 