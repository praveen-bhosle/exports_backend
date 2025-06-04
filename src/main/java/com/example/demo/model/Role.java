package com.example.demo.model;

import java.util.List;

import com.example.demo.enums.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role { 

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name="rolenum")
    private RoleEnum rolenum ; 
    @ManyToMany(mappedBy="userRoles")
    private List<User> users ;   

    public Role( RoleEnum rolenum )  { 
      this.rolenum  = rolenum ;
    }
}