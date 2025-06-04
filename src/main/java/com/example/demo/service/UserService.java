package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.enums.RoleEnum;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;


@Service
public class UserService { 

     @Autowired 
     UserRepository userRepository ;  

     BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder() ;
       
     public User createUser( User user ) { 
        String username = user.getUsername() ;

        String password = user.getPassword() ;  

        String hashedPassword =  passwordEncoder.encode(password) ;    

        Role role = new Role( RoleEnum.USER ) ;  

        List<Role> roleList = new ArrayList<>() ;
        roleList.add(role) ; 
        User newUser = new   User( username , hashedPassword , roleList ) ;    

        return userRepository.save(newUser) ;
     }

    public  void  deleteUser(User user ) { 
       Long id =   user.getId() ;
       userRepository.deleteById(id);
    } 

}