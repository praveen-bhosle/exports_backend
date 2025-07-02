package com.example.messaging_rabbitmq.service;
import org.springframework.stereotype.Service;

import com.example.dto.EmailDetails;

@Service
public interface EmailService {
    String sendSimpleMail(EmailDetails details);   
    String sendMailWithAttachment(EmailDetails details);
  //  String sendMimeMail(EmailDetails emailDetails) ; 
}