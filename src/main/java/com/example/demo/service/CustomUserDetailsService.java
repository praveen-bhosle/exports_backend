package com.example.demo.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.model.User2;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class CustomUserDetailsService implements UserDetailsService {  


    @Autowired 
    UserRepository userRepository ; 

   
    @Override
    @Transactional
    public UserDetails  loadUserByUsername(  String username  ) throws UsernameNotFoundException  { 
        User user = userRepository.findByUsername(username) ;
        if( user == null) { 
            throw new UsernameNotFoundException("User not found.") ;
        } 
       return  new  User2(user)  ;
    } 
}  