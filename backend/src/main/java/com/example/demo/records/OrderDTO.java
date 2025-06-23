package com.example.demo.records;

import java.util.List;

import com.example.demo.model.OrderedProduct;

public record OrderDTO (Long id , List<OrderedProduct> orderedProducts , Long TotalCost)  { 
}