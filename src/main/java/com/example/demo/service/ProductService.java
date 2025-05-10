package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository ;


@Service
public class ProductService {

    @Autowired 
    ProductRepository productRepository  ; 

    public Product  createProduct( Product product ) { 
       return productRepository.save(product) ;
    }

    public Product getProductById(Long id) { 
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() { 
        return productRepository.findAll() ;
    }  

    public Product editProduct( Product product  ) { 
 
        Long id = product.getId() ;

        Product existingProduct = productRepository.findById(id).orElse(null) ; 
        
        if(existingProduct!=null) { 
            existingProduct.setCost(product.getCost()); 
            existingProduct.setStock(product.getStock()); 
        }

        return existingProduct ;

    } 

    public void deleteProduct(Long id ) { 
        productRepository.deleteById(id);
    }
    
}
