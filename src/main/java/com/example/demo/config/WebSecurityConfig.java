package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.component.JwtAuthenticationFilter;
import com.example.demo.service.CustomUserDetailsService;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class WebSecurityConfig { 

   @Autowired
   private  JwtAuthenticationFilter jwtAuthFilter ;  

  @Autowired
  private CustomUserDetailsService userDetailsService ;  


    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http) throws  Exception { 
         http.csrf( AbstractHttpConfigurer::disable) 
             .cors(withDefaults()) 
             .sessionManagement( sessionManagement -> sessionManagement.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) ) 
             .authorizeHttpRequests( authorizeRequests ->  authorizeRequests 
                                                                   .requestMatchers("/api/public/**").permitAll()
                                                                   .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                                                   .requestMatchers("/api/user/**").hasAnyRole("USER" , "ADMIN")
                                                                   .anyRequest().authenticated() 
                                                                   ) ; 

         http.addFilterBefore( jwtAuthFilter ,  UsernamePasswordAuthenticationFilter.class );
         return http.build() ;
    }   


    @Bean 
    public  PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder() ;
     }


     @Bean  AuthenticationProvider authenticationProvider() { 
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider() ; 
        authProvider.setUserDetailsService(userDetailsService) ; 
        authProvider.setPasswordEncoder(passwordEncoder() )  ; 
        return  authProvider  ; 
     }  

     @Bean 
     public AuthenticationManager authenticationManager( AuthenticationConfiguration config ) throws Exception { 
        return  config.getAuthenticationManager() ;
     }
}     