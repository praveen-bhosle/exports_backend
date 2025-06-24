package com.example.demo.model;


import java.util.List;

import com.example.demo.enums.OrderStatusEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    private String id ;
    @ManyToOne
    @JoinColumn(name="username" , referencedColumnName="username")  
    private User user ;
    @OneToMany(mappedBy="order")
    @ToString.Exclude
    private List<OrderedProduct> orderedProducts ;   
    private  Long  totalCost  ; 
    private OrderStatusEnum orderStatus = OrderStatusEnum.UNPAID ;

    @OneToOne
    @JoinColumn(name="transaction_id")
    private Transaction transaction ; 

    public  Order( String id , Long totalCost ,  User user ) { 
        this.id = id ; 
        this.totalCost = totalCost ;
        this.user = user  ; 
    }

}