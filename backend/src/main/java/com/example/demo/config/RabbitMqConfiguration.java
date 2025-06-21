package com.example.demo.config;

import java.util.Map;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableRabbit
public class RabbitMqConfiguration { 
    private final AmqpAdmin rabbitAdmin  ;  

    public RabbitMqConfiguration(AmqpAdmin  rabbitAdmin ) { 
        this.rabbitAdmin = rabbitAdmin ; 
    } 

    @PostConstruct
    public void declareQueue() { 

        String name = "my-queue" ; 
        String routingKey = "my-key" ; 
        boolean durable  = true  ; 
        boolean exclusive = false ; 
        boolean autoDelete = false ; 

        String queueName = rabbitAdmin.declareQueue( new Queue( name , durable ,exclusive , autoDelete) ) ; 

        String exchangeName = "my-exchange" ; 

        DirectExchange exchange = new DirectExchange(  exchangeName ,  durable , autoDelete )  ; 

        rabbitAdmin.declareExchange(exchange); 

        DestinationType destinationType = DestinationType.QUEUE ; 
        
        Map<String,Object> arguments = null ; 

        Binding binding = new Binding(queueName, destinationType, exchangeName, routingKey, arguments) ; 

        rabbitAdmin.declareBinding(binding);
    }
}