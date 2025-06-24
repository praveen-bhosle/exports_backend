package com.example.demo.controller.userControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.records.OrderDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.RabbitMQService;
import com.example.demo.service.TransactionService;

@RestController
@RequestMapping("/api/user/order")  
public class UserOrderController {   

   

   @Autowired 
   UserRepository userRepository ; 

   @Autowired 
   RabbitMQService rabbitMQService ; 

   @Autowired
   OrderService orderService ;

   @Autowired 
   TransactionService transactionService ; 

   @GetMapping("")
   public ResponseEntity<?>   getOrders() {   
    try { 
           List<OrderDTO> orders = orderService.getUserOrders() ;  
           return  new ResponseEntity<>( orders , HttpStatus.OK )  ;   
    } 
    catch( Exception  e) { 
        return  new ResponseEntity<>("Internal server error"  ,  HttpStatus.INTERNAL_SERVER_ERROR) ; 
    } 
   } 
}