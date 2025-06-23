package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.GetUsername;

@Service
public class OrderService {
    
      @Autowired 
      private UserRepository userRepository ; 

      @Autowired
      private OrderRepository orderRepository ; 

      public Order createOrder( Long TotalCost )  {  
            String username = GetUsername.getUsername() ; 
            User user = userRepository.findByUsername(username) ;
            Order order = new Order(TotalCost , user) ; 
            return  orderRepository.save(order) ; 
      }
}
