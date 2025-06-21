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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CartProductRequestBody;
import com.example.demo.model.Product;
import com.example.demo.records.CartProductDTO;
import com.example.demo.service.CartProductService;
import com.example.demo.service.ProductService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/user/cart")
public class CartController { 
    @Autowired 
    private CartProductService cartProductService; 
    @Autowired 
    private ProductService productService ;

  @GetMapping("") 
   public  ResponseEntity<?>   GetProducts() {  
        List<CartProductDTO> products  =  cartProductService.getCartProducts() ;   
        Map<String,List<CartProductDTO>> map  = new HashMap<>()  ;  
        map.put("cartItems", products) ; 
        return new ResponseEntity<>(  map ,  HttpStatus.OK) ;
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
        return  new ResponseEntity<>("Product added to cart" , HttpStatus.OK) ; }  
        catch( NumberFormatException e   ) { 
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST) ; 
        } 
        catch( Exception e   ) { 
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
        }
   } 
   
   @PutMapping("")
   public ResponseEntity<?> EditProduct( @Valid @RequestBody CartProductRequestBody  cartProduct  ) {   
      try { 
        Long id = cartProduct.getId();
        Long quanity = cartProduct.getQuantity() ;
        if(!cartProductService.checkIfBelongsToUser(id))  { 
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST ) ; 
        } 
        cartProductService.editCartProduct(id, quanity) ;
        return new ResponseEntity<>("Product edited" ,HttpStatus.OK)  ; 
      } 
      catch( Exception e  ){ 
        System.out.println(e.getMessage()) ;
        return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR) ;
        }
   }
 }