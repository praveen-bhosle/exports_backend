package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Image { 
@Id
private String  name ;  
@ManyToMany(mappedBy="images")
@JsonIgnore
@ToString.Exclude
private List<Product> products ; 
}