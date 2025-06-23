package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.enums.RoleEnum;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.AuthBody;
import com.example.demo.model.User;
import com.example.demo.records.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.GetUsername;


@Service
public class UserService { 

     @Autowired 
     UserRepository userRepository ;  

     @Autowired 
     UserMapper userMapper ; 

     BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder() ;
       
     public User createUser( AuthBody authBody  ) { 
        String username = authBody.getUsername() ;

        String password = authBody.getPassword() ;  

        String hashedPassword =  passwordEncoder.encode(password) ;    

        RoleEnum role =  RoleEnum.USER ;  

        User newUser = new   User( username , hashedPassword , role  ) ;    

        return userRepository.save(newUser) ;
     }

    public  void  deleteUser(User user ) { 
       Long id =   user.getId() ;
       userRepository.deleteById(id);
    } 

    public UserDTO  getUser( ) {  
      String username = GetUsername.getUsername() ;  
      User user = userRepository.findByUsername(username) ; 
      return userMapper.userDTO(user) ; 
    }

}