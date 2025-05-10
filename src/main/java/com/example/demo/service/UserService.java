package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;;

@Service
public class UserService { 

     @Autowired 
     UserRepository userRepository ;  

     BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder() ;
       
     public User createUser( User user ) { 
        String email = user.getEmail() ;

        String password = user.getPassword() ;  

        String hashedPassword =  passwordEncoder.encode(password) ;   

        User newUser = new   User( email , hashedPassword ) ;    

        return userRepository.save(newUser) ;
     }

     public boolean verifyUserByEmail( User user ) {  
        String email = user.getEmail() ;
        String password = user.getPassword() ;  
        String hashedPassword =  passwordEncoder.encode(password) ; 
        User existingUser =  userRepository.findByEmail(email) ;  
        return existingUser != null  && existingUser.getPassword().equals(hashedPassword)  ;
    } 
       
    public boolean verifyUserByPhone( User user  ) {  
        String phone  = user.getPhone() ;
        String password = user.getPassword() ;  
        String hashedPassword =  passwordEncoder.encode(password) ; 
        User existingUser =  userRepository.findByPhone(phone) ;  
        return existingUser != null  && existingUser.getPassword().equals(hashedPassword)  ;
    }

    public  void  deleteUser(User user ) { 
       Long id =   user.getId() ;
       userRepository.deleteById(id);
    } 

}