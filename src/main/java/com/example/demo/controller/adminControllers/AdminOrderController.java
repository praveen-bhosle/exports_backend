package com.example.demo.controller.adminControllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;

@RestController 
@RequestMapping("/api/admin/orders")
public class AdminOrderController { 

    @Autowired 
    OrderRepository orderRepository ; 
        
    @GetMapping("/all") 
    public  ResponseEntity<?> getAllOrders()   {       
        List<Order> orders = orderRepository.findAll() ;  
        Map<String,List<Order>> map  = new HashMap<>() ;  
        map.put( "orders"  , orders ) ;
        return  new ResponseEntity<>(  map  ,  HttpStatus.ACCEPTED ) ; 
    } 

    @GetMapping("/byUserId/{username}") 
    public  ResponseEntity<?> getOrdersByUserId( @PathVariable String username  )   {   
    
        
        List<Order> orders = orderRepository.getOrdersByUserUsername(username) ;  
        Map<String,List<Order>> map  = new HashMap<>() ;  
        map.put( "orders"  , orders ) ;
        return  new ResponseEntity<>(  map  ,  HttpStatus.ACCEPTED ) ; 
    }   

    @GetMapping("/{id}") 
    public  ResponseEntity<?> byId( @PathVariable String  id  ) {  
  
        try { 
         Long  parsedId  =   Long.valueOf(id) ;     
         Order  order  = orderRepository.findById(parsedId).orElse(null) ; 
         
            Map<String ,  Order> map = new HashMap<>() ; 
            map.put("order", order) ;
            return new  ResponseEntity<>( map ,  HttpStatus.ACCEPTED  ) ;  } 
        catch( NumberFormatException e  ) { 
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST ) ;
        }
    } 
 }