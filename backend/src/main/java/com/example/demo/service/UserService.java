package com.example.demo.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.enums.EmailTypeEnum;
import com.example.demo.enums.RoleEnum;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.AuthBody;
import com.example.demo.model.EmailDetails;
import com.example.demo.model.EmailTemplate;
import com.example.demo.model.User;
import com.example.demo.records.UserDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.EmailTemplates.PasswordResetConfirmationEmailTemplate;
import com.example.demo.utils.GetUsername;


@Service
public class UserService { 

     @Autowired 
     UserRepository userRepository ;  

     @Autowired 
     UserMapper userMapper ; 

     @Autowired 
     RabbitMQService rabbitMQService ; 

     BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder() ;
       
     public User createUser( AuthBody authBody  ) { 
        String username = authBody.getUsername() ;

        String password = authBody.getPassword() ;  

        String hashedPassword =  passwordEncoder.encode(password) ;    

        RoleEnum role =  RoleEnum.USER ;  

        User newUser = new   User( username , hashedPassword , role  ) ;    

        return userRepository.save(newUser) ;
     }

     public boolean updatePassword(  String username ,  String password  )  { 
      User user = userRepository.findByUsername(username) ; 
      if(user == null) { return false ; } 
      String hashedPassword = passwordEncoder.encode(password) ;
      user.setPassword(hashedPassword);
      userRepository.save(user) ; 
      return true ; 
     }

    public  void  deleteUser(User user ) { 
       UUID id =   user.getId() ;
       userRepository.deleteById(id);
    } 

    public UserDTO  getUser( ) {  
      String username = GetUsername.getUsername() ;  
      User user = userRepository.findByUsername(username) ; 
      return userMapper.userDTO(user) ; 
    } 

   
    public UserDTO  getUserByUsername(String username) { 
      User user = userRepository.findByUsername(username) ; 
      return userMapper.userDTO(user) ; 
    } 

    public void sendEmail(EmailTypeEnum emailTye ) { 
      sendEmail(  emailTye  ,  GetUsername.getUsername()) ; 
    }
    public void sendEmail( EmailTypeEnum  emailType ,  String username )  {  
      userRepository.findByUsername(username) ; 
    }   

    public void sendPasswordResetConfirmationEmail( String username  )  {  
       User user  =  userRepository.findByUsername(username) ; 
       String email =  user.getEmail() ;  
       String name = user.getProfile().getFirstName() ;  
       EmailTemplate emailTemplate =   PasswordResetConfirmationEmailTemplate.getEmailTemplate(username, name   , email) ; 
       EmailDetails emailDetails = new EmailDetails( email ,  emailTemplate.getBody() , emailTemplate.getSubject() ,   null  )  ;   
       rabbitMQService.sendMessage(emailDetails);
    } 




}