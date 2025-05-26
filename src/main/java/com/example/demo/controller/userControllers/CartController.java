package com.example.demo.controller.userControllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CartProduct;
import com.example.demo.model.Product;
import com.example.demo.service.CartProductService;
import com.example.demo.service.ProductService;



@RestController
@RequestMapping("/api/user/cart")
public class CartController { 
    @Autowired 
    private CartProductService cartProductService; 
    @Autowired 
    private ProductService productService ;

  @GetMapping("") 
   public  ResponseEntity<?>   GetProducts() {  
        List<CartProduct> products  =  cartProductService.getCartProducts() ;   
        Map<String,List<CartProduct>> map  = new HashMap<>()  ;  
        map.put("Products", products) ; 
        return new ResponseEntity<>(  map ,  HttpStatus.ACCEPTED ) ;
   }

   @PostMapping("/{id}") 
   public ResponseEntity<?> AddProduct(  @PathVariable String id   ) {  
 
    try {  
    Long productId = Long.valueOf(id) ;  
    Product product = productService.getProductById(productId);    
    if( product == null  ) { 
        return   new ResponseEntity<>(HttpStatus.BAD_REQUEST) ; 
    } 
    boolean alreadyPresent = cartProductService.checkIfPresentByProductId(product) ;  
    if( alreadyPresent ) { 
        return  new ResponseEntity<>( "The product is already added to the cart." ,  HttpStatus.ACCEPTED ) ; 
    }
    cartProductService.addToCart(product) ;     
    return  new ResponseEntity<>("Product added to cart" , HttpStatus.CREATED) ; }  

    catch( NumberFormatException e   ) { 
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ; 
    } 
    catch( Exception e   ) { 
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
        }

    } 

   @DeleteMapping("/{id}") 
   public ResponseEntity<?>   DeleteProducts( @PathVariable  String  id  ) { 
 
    try {  
        Long cartProductId  = Long.valueOf(id) ; 
        boolean verified = cartProductService.checkIfBelongsToUser(cartProductId) ;  
        if( !verified ) { return  new ResponseEntity<>(   HttpStatus.BAD_REQUEST ) ; }
        cartProductService.deleteFromCart(cartProductId);
        return  new ResponseEntity<>("Product added to cart" , HttpStatus.CREATED) ; }  
        catch( NumberFormatException e   ) { 
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ; 
        } 
        catch( Exception e   ) { 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
        }
   } 
}
