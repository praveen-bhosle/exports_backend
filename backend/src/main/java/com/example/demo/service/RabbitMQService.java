package com.example.demo.service;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {
    @Autowired
    private RabbitTemplate rabbitTemplate ;  

    private final  String exchange   = "my-exchange" ; 
    private final  String routingKey = "my-key" ; 

    public void sendMessage(  Object message) {  
        rabbitTemplate.convertAndSend(exchange , routingKey , message );
    }
}