package com.example.demo.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.demo.component.JwtAuthenticationFilter;
import com.example.demo.service.CustomUserDetailsService;



@Configuration
@EnableWebSecurity


public class WebSecurityConfig { 

   @Autowired
   private  JwtAuthenticationFilter jwtAuthFilter ;  

  @Autowired
  private CustomUserDetailsService userDetailsService ;  


    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http) throws  Exception { 
         http.csrf( AbstractHttpConfigurer::disable) 
             .cors( c -> c.configurationSource(corsConfigurationSource()) ) 
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
    //903599

      @Bean
    public CorsConfigurationSource corsConfigurationSource() {  
        System.out.println("setting up cors.");
       // System.out.println( SecurityContextHolder.getContext().getAuthentication().getAuthorities() );
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173","https://fancy-cendol-1295ff.netlify.app" ,  "https://exports-frontend.vercel.app" , "https://ykdevoutexports.com" ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS" ));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
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
