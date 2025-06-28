package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.mapper.Order2Mapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.CartProduct;
import com.example.demo.model.Order;
import com.example.demo.model.OrderedProduct;
import com.example.demo.model.Product;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.records.OrderDTO;
import com.example.demo.records.OrderDTO2;
import com.example.demo.records.TransactionDTO;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderedProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.GetUsername;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    
      @Autowired 
      private UserRepository userRepository ; 
  
      @Autowired 
      private CartProductService cartProductService ; 

      @Autowired 
      private OrderRepository orderRepository ; 

      @Autowired 
      private OrderedProductRepository orderedProductRepository ;

      @Autowired 
      private TransactionService transactionService ; 

      @Autowired 
      private OrderMapper orderMapper  ; 

      @Autowired 
      private Order2Mapper order2Mapper ; 

      
      @Transactional
      public void  createOrder(  String id ,  Long TotalCost   )  {   
        try { 
            String username = GetUsername.getUsername() ;
            User user = userRepository.findByUsername(username) ;
            Order order = new Order( id , TotalCost , user) ;  
            Order createdOrder  = orderRepository.save(order) ;
            List<CartProduct> cartProducts  =  cartProductService.getRawCartProducts(); 
            
            for( CartProduct  item :   cartProducts) { 
                 Long quantity    =  item.getQuantity()   ; 
                 Product product =  item.getProduct() ; 
                 OrderedProduct orderedProduct = new OrderedProduct( product ,  createdOrder , quantity ) ; 
                 orderedProductRepository.save(orderedProduct) ;
            }  
        } 
        catch (Exception e  ) { 
            System.out.println(e.getMessage()); 
            throw new  RuntimeException(e) ; 
           } 
      }
      
      @Transactional 
      public void ValidateOrder(String orderId , TransactionDTO transactionDTO) {  
        Transaction transaction = transactionService.createTransaction(transactionDTO) ; 
        System.err.println(transaction); 
        Order order = orderRepository.findById(orderId).orElse(null) ; 
        order.setOrderStatus(OrderStatusEnum.PAID);  
        order.setTransaction(transaction);
        orderRepository.save(order) ;
      }

      public List<OrderDTO> getUserOrders() { 
        String username =  GetUsername.getUsername() ; 
        return orderRepository.findByUserUsername(username).stream().map(orderMapper::toDTO).toList()  ;
      }

      public OrderDTO2 getUserOrder( String orderId ) { 
        
        String username = GetUsername.getUsername() ;  
        Order order =  orderRepository.findById(orderId).orElse(null) ; 
        if (order == null  ||   !username.equals(order.getUser().getUsername()) ) { 
          return null ;
        }    
        return  order2Mapper.toDTO(order) ;  
      }
}