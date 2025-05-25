package com.example.demo.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; 

@Entity
@Table(name="Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements  UserDetails   {   
 

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY ) 
    private Long id ; 

    @Column(unique=true) 
    private String username ; 

    private String password ; 
    private String email ; 

    @ElementCollection(fetch=FetchType.EAGER) 
    private Set<String>roles = new HashSet<>() ;

    @OneToMany(mappedBy="user")
    private List<Order> orders ; 


    @OneToMany(mappedBy="user")
    private List<AddedToCartProduct>  productsAddedToCart ;   

     
    public  User( String username ,  String password ,  Set<String> roles )  { 
        this.password = password ; 
        this.username = username ;
        this.roles  = roles ;
    }

    @Override
    public Collection<? extends   GrantedAuthority >    getAuthorities () {    
        return this.roles.stream().map ( role -> new SimpleGrantedAuthority( "ROLE_" + role  )).collect(Collectors.toList()) ;  
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