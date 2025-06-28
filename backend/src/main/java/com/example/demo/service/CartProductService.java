
package  com.example.demo.service ;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.CartProductMapper;
import com.example.demo.model.CartProduct;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.records.CartProductDTO;
import com.example.demo.repository.CartProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.GetUsername;

@Service
public  class CartProductService  {   

    @Autowired
    private  CartProductRepository cartProductRepository ;   

    @Autowired 
    private UserRepository userRepository ; 

    @Autowired 
    private CartProductMapper cartProductMapper ;

    public   boolean  checkIfPresentByProductId ( Product product  )  { 
        String username = GetUsername.getUsername() ;   
        List<CartProduct> products  =  cartProductRepository.findByUserUsername(username) ;    
        for(  CartProduct cartProduct : products ) {  
            if ( cartProduct.getProduct().getId().equals( product.getId() )  )   { 
                 return true ; 
            }
        } 
        return false ; 
    } 

    public boolean   checkIfPresentById( UUID id  ) { 
        CartProduct cartProduct = cartProductRepository.findById(id ).orElse(null) ;   
        return  cartProduct != null ; 
    }

    public  CartProduct addToCart( Product product ) {    
        String username = GetUsername.getUsername() ;   
        User user  = userRepository.findByUsername(username) ;      
        CartProduct  cartProduct = new CartProduct(user  , product) ; 
        return  cartProductRepository.save(cartProduct) ; 
    }

    public  List<CartProductDTO>  getCartProducts() { 
        String username = GetUsername.getUsername() ;   
        return   cartProductRepository.findByUserUsername(username).stream().map(cartProductMapper::toDTO).toList() ; 
    }  

    public List<CartProduct> getRawCartProducts() { 
        String username = GetUsername.getUsername() ; 
        return  cartProductRepository.findByUserUsername(username) ; 
    }



    public  boolean  checkIfBelongsToUser( UUID id  ) {    
        CartProduct cartProduct  = cartProductRepository.findById(id).orElse(null) ;  
        String username = GetUsername.getUsername() ; 
        return  cartProduct != null &&  cartProduct.getUser().getUsername().equals(username) ;
    }

    public void deleteFromCart(  UUID id   ) { 
    cartProductRepository.deleteById(id) ; 
    } 

    public CartProduct editCartProduct( UUID id , Long quantity )  {     
        CartProduct cartProduct = cartProductRepository.findById(id).orElse(null) ; 
        cartProduct.setQuantity(quantity); 
        return  cartProductRepository.save(cartProduct) ;
    }  
}