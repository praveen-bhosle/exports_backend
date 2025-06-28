package com.example.demo.controller.adminControllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Product;
import com.example.demo.records.ProductDTO;
import com.example.demo.service.ProductService;



@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController { 
    
    @Autowired 
    ProductService productService ;  
     
    @PostMapping("/create") 
    public Product createProduct(@RequestBody Product product) { 
       return  productService.createProduct(product) ;
    }

    @GetMapping("") 
    public  List<ProductDTO> getAllProducts() { 
        return productService.getAllProducts() ;
    }

    @GetMapping("/{id}") 
    public Product getProduct(@PathVariable UUID id ) { 
        return productService.getProductById(id) ;
    }

    @DeleteMapping("/{id}") 
    public void deleteProduct(@PathVariable UUID id ) { 
        productService.deleteProduct(id);
    } 

    @PutMapping("/edit") 
    public Product editProduct(@RequestBody Product product) { 
        return productService.editProduct(product) ;
    }
    
}