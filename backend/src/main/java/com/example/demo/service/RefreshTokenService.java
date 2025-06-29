package com.example.demo.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.RefreshToken;
import com.example.demo.model.User;
import com.example.demo.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService { 


    @Autowired 
    RefreshTokenRepository refreshTokenRepository ; 


    public RefreshToken createToken(User user ) {  
    RefreshToken  refreshToken = new RefreshToken() ; 
    refreshToken.setUser(user) ; 
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setExpiryDate(Instant.now().plus(7,ChronoUnit.DAYS)); 
    return refreshTokenRepository.save(refreshToken) ; 
    }
    

    public User validateToken( String token ) { 
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token) ; 
        if( Instant.now().isBefore(refreshToken.getExpiryDate()) )  { 
            return  refreshToken.getUser() ;
        }
        return null ; 
    }
}
