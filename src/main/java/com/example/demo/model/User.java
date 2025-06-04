package com.example.demo.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @OneToMany(mappedBy="user")
    private List<Order> orders ; 

    @OneToMany(mappedBy="user")
    private List<CartProduct>  productsAddedToCart ;   

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable( name="user_roles" , joinColumns= @JoinColumn( name="user_id") , inverseJoinColumns=@JoinColumn(name="role_rolenum") ) 
    private List<Role> userRoles ;
     
    public  User( String username ,  String password ,  List<Role> roles )  { 
        this.password = password ; 
        this.username = username ;
        this.userRoles = roles ;
    }

    @Override
    public Collection<? extends   GrantedAuthority >    getAuthorities () {   
        System.err.println("getting user authorities"); 
        System.out.println(this.userRoles);   
        return this.userRoles.stream().map ( role -> new SimpleGrantedAuthority( "ROLE_" + role.getRolenum()  )).collect(Collectors.toList()) ;  
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