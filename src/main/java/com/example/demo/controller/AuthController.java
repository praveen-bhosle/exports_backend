package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController { 
 
    @Autowired 
    UserService userService ;

    @PostMapping("/loginByEmail")  
    public boolean loginByEmail( @RequestBody User user   ) { 
        return  userService.verifyUserByEmail(user) ; 
    } 

    @PostMapping("/loginByPhone") 
    public  boolean  loginByPhone(@RequestBody User user) { 
        return userService.verifyUserByPhone(user) ; 
    }

    @PostMapping("/signup") 
    public User   signup(@RequestBody User user)  {
        return  userService.createUser(user) ;
    } 

    @DeleteMapping("/deleteuser") 
    public void deleteUser(@RequestBody User user) { 
        userService.deleteUser(user); 
    } 
  
}