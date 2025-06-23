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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Order;
import com.example.demo.records.TransactionDTO;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.RabbitMQService;
import com.example.demo.service.TransactionService;
import com.example.demo.utils.GetUsername;
import com.example.dto.EmailDetails;

@RestController
@RequestMapping("/api/user/order")  
public class UserOrderController {   

   @Autowired 
   OrderRepository orderRepository ; 

   @Autowired 
   UserRepository userRepository ; 

   @Autowired 
   RabbitMQService rabbitMQService ; 

   @Autowired
   OrderService orderService ;

   @Autowired 
   TransactionService transactionService ; 

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
    public  ResponseEntity<?> createOrder(  @RequestParam Long totalCost , @RequestBody TransactionDTO transactionDTO    ) {    
     try { 
        if(totalCost == null) {  return new ResponseEntity<>("TotalCost absent" , HttpStatus.BAD_REQUEST )  ; }   
        if(!transactionService.verifyTransaction(transactionDTO))  return new ResponseEntity<>("Unauthorised request" , HttpStatus.UNAUTHORIZED ) ; 
        Order  createdOrder = orderService.createOrder(totalCost) ; 
        String email  = userRepository.findByUsername(username).getEmail() ;    
        if( email == null ) {  
            return  new  ResponseEntity<>( "Account is not connected with an email."  ,  HttpStatus.BAD_REQUEST ) ;    
        }
        EmailDetails emailDetails = new EmailDetails( email  ,    "Order created successfully. Order ID : " + createdOrder.getId() + "." ,  "Order Confirmation" ,   null     ) ; 
        rabbitMQService.sendMessage(emailDetails); 
        return new   ResponseEntity<>(  createdOrder  ,  HttpStatus.CREATED )   ;   
     } 
     catch( Exception e ) { 
         return new ResponseEntity<>( "Internal server error." ,  HttpStatus.INTERNAL_SERVER_ERROR) ; 
     } 
    } 
}