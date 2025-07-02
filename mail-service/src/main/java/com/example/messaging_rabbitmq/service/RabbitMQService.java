package com.example.messaging_rabbitmq.service;

import java.io.IOException;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.EmailDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RabbitMQService {
    @Autowired
    private RabbitTemplate rabbitTemplate ;  

    @Autowired 
    private EmailServiceImpl emailService ;

    private final  String exchange   = "my-exchange" ; 
    private final  String routingKey = "my-key" ; 

    public void sendMessage(  Object message) {  
        rabbitTemplate.convertAndSend(exchange , routingKey , message );
    }

    @RabbitListener(queues="my-queue")
    public void handleMessage(Message message) throws  Exception { 
        System.out.println("Received message: " + message  );  
        try{
          byte[] data =  message.getBody() ; 
          ObjectMapper objectMapper = new ObjectMapper() ;
          EmailDetails emailDetails =  objectMapper.readValue(data, EmailDetails.class) ; 
          System.out.println("hi there");
          System.out.println("Email " + emailDetails.getReceiver() ) ; 
          String  response = emailService.sendSimpleMail(emailDetails) ; 
          System.out.println(response); 
          if(response.equals("Error while sending mail") ) { 
            throw new Exception("Failed to send the message , requeuing it." ) ; 
          }
        }
        catch( IOException | ClassNotFoundException e ) { 
            System.err.println("Deserialization failed: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            System.err.println(e.getMessage());
            throw new AmqpRejectAndDontRequeueException("Fail and don't requeue");
        } catch( ClassCastException e  ) { 
            System.err.println("Message is not a byte array.");
        }
      }
}