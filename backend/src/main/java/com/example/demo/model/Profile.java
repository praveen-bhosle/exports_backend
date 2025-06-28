package com.example.demo.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="profile")
public class Profile {
    @Id
    @GeneratedValue
    private UUID id ; 
    private String firstName ;
    private String lastName  ; 
    private String image ; 
}