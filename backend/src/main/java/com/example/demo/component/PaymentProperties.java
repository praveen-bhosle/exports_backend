package com.example.demo.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@ConfigurationProperties(prefix="payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProperties {
    private String id ; 
    private String secret ; 
}
