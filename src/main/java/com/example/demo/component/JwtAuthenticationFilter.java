package com.example.demo.component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException ;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtAuthenticationFilter extends  OncePerRequestFilter   { 
    @Autowired
    private JwtService jwtService ;  

    @Autowired
    private CustomUserDetailsService customUserDetailsService ; 

    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request ,  @NonNull HttpServletResponse response  ,  @NonNull FilterChain filterChain )  throws ServletException ,  IOException {    
 

        String path = request.getServletPath() ; 

        if(path.startsWith("/api/public")) { 
            filterChain.doFilter(request, response);
            return ;
        }
     
        try { 

        String authHeader = request.getHeader("Authorization") ; 
        System.out.println(authHeader) ;
        String username =  null  ; 
        String token  ; 

        

        if( authHeader != null && authHeader.startsWith("Bearer ")) { 
               token  = authHeader.substring(7) ;
               System.out.println(token);
               username = jwtService.getUsername(token) ;
               System.out.println(username) ; 
        } 

        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        
        if(  username != null  &&  !username.equals("Error")    && SecurityContextHolder.getContext().getAuthentication() == null   ) { 

            System.err.println("Fetching user details."); 
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username) ;  

            System.out.println(userDetails); 

          //  System.out.println(userDetails.getAuthorities());
        
            System.out.println("getting  token");

            List <GrantedAuthority>  auth = new ArrayList<>() ;  

            auth.add(new SimpleGrantedAuthority("Role_User")) ; 
        
           UsernamePasswordAuthenticationToken  authenticationToken = new UsernamePasswordAuthenticationToken( userDetails , null , auth ) ;  

           authenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request) );

           SecurityContextHolder.getContext().setAuthentication(authenticationToken);

          }    

        filterChain.doFilter(request, response); 
     } 
        catch( ServletException | IOException e  ) {    
            System.out.println(e.getMessage());
            filterChain.doFilter(request, response); 
        }
    } 
}