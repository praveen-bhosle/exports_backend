package com.example.demo.controller.publicControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.records.ProductDTO;
import com.example.demo.service.ProductService;


@RestController
@RequestMapping("/api/public/products")
public class PublicProuctController {

    @Autowired
    private ProductService productService ; 

    @GetMapping("") 
    public  List<ProductDTO> getAllProducts() { 
        return productService.getAllProducts() ;
    }
}
