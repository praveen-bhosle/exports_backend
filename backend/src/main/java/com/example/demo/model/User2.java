package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.enums.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User2 implements  UserDetails   {   
 
    @Id
    @GeneratedValue
    private UUID id ; 

    @Column(unique=true) 
    private String username ; 

    private String password ; 
    private String email ; 

    @Enumerated(EnumType.STRING)
    private RoleEnum role ; 

    public  User2(   User user   ) { 
        username = user.getUsername() ; 
        password = user.getPassword() ;
        role     = user.getRole()     ;
    } 
     
    @Override
    public Collection<? extends   GrantedAuthority >    getAuthorities () {   
        List<RoleEnum> roles = new ArrayList<>() ;
        roles.add(this.role) ;
        return roles.stream().map (  roleInArray   -> new SimpleGrantedAuthority( "ROLE_" + roleInArray  )).collect(Collectors.toList()) ;  
    } 

    @Override 
    public  boolean  isAccountNonExpired() { 
        return true ;
    } 

    @Override 
    public boolean isAccountNonLocked() { 
        return true ; 
    }

    @Override 
    public boolean isCredentialsNonExpired() { 
        return true ;
    }

    @Override 
    public boolean  isEnabled() { 
        return true ;
    }
}