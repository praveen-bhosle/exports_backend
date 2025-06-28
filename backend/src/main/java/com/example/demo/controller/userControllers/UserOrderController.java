package com.example.demo.controller.userControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.records.OrderDTO;
import com.example.demo.records.OrderDTO2;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/api/user/order")  
public class UserOrderController {   

   @Autowired
   OrderService orderService ;
   
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

   @GetMapping("/orderId") 
   public ResponseEntity<?> getOrder( @RequestParam String id ) {
    try {  
        System.err.println(id); 
        OrderDTO2 order = orderService.getUserOrder(id) ; 
        if(order == null) return  new ResponseEntity<>( "Bad request." , HttpStatus.BAD_REQUEST ) ; 
        return  new ResponseEntity<>( order  , HttpStatus.OK )  ;   
    } 
    catch( Exception  e) { 
        return  new ResponseEntity<>("Internal server error"  ,  HttpStatus.INTERNAL_SERVER_ERROR) ; 
    } 
   } 

}