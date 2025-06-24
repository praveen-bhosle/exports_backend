package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderedProduct;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderedProductRepository;


@Service
public class OrderedProductService {
 
    @Autowired 
    OrderedProductRepository orderedProductRepository ; 

    public void create(Product product ,  Long quantity ,  Order order   ) {   
        OrderedProduct orderedProduct = new OrderedProduct( product , order ,  quantity  ) ;  
        orderedProductRepository.save(orderedProduct) ; 
    }
    
    


}