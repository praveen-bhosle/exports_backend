package com.example.demo.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartProductRequestBody { 
    @NotNull
    private Long quantity;
    @NotNull
    private Long id;
}