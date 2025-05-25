package com.example.demo.controller.userControllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.EmailDetails;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;
import com.example.demo.utils.GetUsername;

@RestController
@RequestMapping("/api/user/order")  
public class UserOrderController {   

    @Autowired 
    OrderRepository orderRepository ; 

    @Autowired  
   EmailService emailService ;   

   @Autowired 
   UserRepository userRepository ; 


   String username =  GetUsername.getUsername() ; 

    @GetMapping("/my-orders")
    public ResponseEntity<?>   getMyOrders() {   
        if(  username != null ) {    
           List<Order> orders = orderRepository.getOrdersByUserUsername(username);   

        Map<String,List<Order>> map = new HashMap<>() ;  

        map.put( "orders"  ,  orders) ;  

          return  new ResponseEntity<>( map , HttpStatus.ACCEPTED )  ;  
        }  
        return new  ResponseEntity<>( "Unauthorised Request" , HttpStatus.UNAUTHORIZED ) ;     
    
    } 
    
    
    @PostMapping("")  
    public  ResponseEntity<?> createOrder(  @RequestBody Order order  ) {    
     try { 
        Order  createdOrder =  orderRepository.save(order) ; 
        String email  = userRepository.findByUsername(username).getEmail() ;    
        if( email == null ) {  
            return  new  ResponseEntity<>( "Account is not connected with an email."  ,  HttpStatus.BAD_REQUEST ) ;    
        }
         EmailDetails emailDetails = new EmailDetails( email  ,    "Order created successfully. Order ID : " + createdOrder.getId() + "." ,  "Order Confirmation" ,   null     ) ; 
         emailService.sendSimpleMail(emailDetails) ;   
        return new   ResponseEntity<>(  "Order created successfully."  ,  HttpStatus.CREATED )   ;   
     } 
     catch( Exception e ) { 
         return new ResponseEntity<>( "Internal server error." ,  HttpStatus.INTERNAL_SERVER_ERROR) ; 
     } 
    } 
}