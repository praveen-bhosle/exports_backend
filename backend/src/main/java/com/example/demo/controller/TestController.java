package com.example.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RabbitMQService;
import com.example.demo.utils.SerializationUtil;
import com.example.dto.EmailDetails;




@RestController
@RequestMapping("/api/public/send")
public class TestController {

  @Autowired 
  private RabbitMQService rabbitMQService ; 

    @PostMapping("")
    public String  x( @RequestBody  EmailDetails emailDetails  )  {   
         try {
             byte[] ans = SerializationUtil.serialize(emailDetails);
             rabbitMQService.sendMessage(ans);
             return "done";
             } 
         catch (IOException e) { return "error occurred"; }
    }
}