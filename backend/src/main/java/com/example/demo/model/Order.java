package com.example.demo.model;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Orders") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ;
    @ManyToOne
    @JoinColumn(name="username" , referencedColumnName="username")  
    private User user ;
    @OneToMany(mappedBy="order")
    @ToString.Exclude
    private List<OrderedProduct> orderedProducts ;   
    private  Long  TotalCost  ; 

    public  Order( Long TotalCost ,  User user ) { 
        this.TotalCost = TotalCost ;
        this.user = user  ; 
    }
}