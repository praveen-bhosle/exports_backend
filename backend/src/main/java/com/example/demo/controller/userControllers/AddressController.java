package com.example.demo.controller.userControllers;

import java.util.List;
import java.util.UUID;

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

import com.example.demo.records.AddressDTO;
import com.example.demo.service.AddressService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/user/address")
public class AddressController {   
  
    @Autowired
    private AddressService addressService ; 

    @GetMapping("") 
    public ResponseEntity<?> getAddresses() { 
        try {
            List<AddressDTO> address = addressService.getAddresses() ;
            return  new ResponseEntity<>(address , HttpStatus.OK) ; 
        } catch (Exception e) { 
            System.out.println(e.getMessage()); 
            return new ResponseEntity<>( "Internal server eroror" ,  HttpStatus.INTERNAL_SERVER_ERROR) ; 
        } 
    }

    @PostMapping("")
    public  ResponseEntity<?>  createAddress( @Valid @RequestBody AddressDTO  address ) {  
        try {  
            addressService.createAddress(address); 
            return  new ResponseEntity<>("Address added successfully."  ,  HttpStatus.CREATED) ; 
        } 
        catch(Exception e) { 
            System.out.println(e.getMessage()); 
            return new ResponseEntity<>( "Internal server eroror" ,  HttpStatus.INTERNAL_SERVER_ERROR) ; 
        } 
    }
    
    @PutMapping("") 
    public ResponseEntity<?> editAddress(@Valid @RequestBody AddressDTO address) { 
        try { 
            if(address.id() == null ) { return new ResponseEntity<>("Invalid request body." , HttpStatus.BAD_REQUEST  ) ; }  
            boolean validation = addressService.validate(address.id()) ;  
            if(!validation) { return new ResponseEntity<>("Invalid request body." ,  HttpStatus.BAD_REQUEST ) ;} // if user tries to access somebody else's address.
            addressService.editAddress(address);
            return new ResponseEntity<>("Address edited successfully." , HttpStatus.OK  ) ; 
        }
        catch(Exception e) { 
            System.err.println(e.getMessage());
            return new ResponseEntity<>( "Internal server eroror" ,  HttpStatus.INTERNAL_SERVER_ERROR) ; 
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable UUID id ) {  
        try { 
            boolean validation = addressService.validate(id) ; 
            if(!validation) { return new ResponseEntity<>("Invalid reqeust body." ,  HttpStatus.BAD_REQUEST) ;  } 
            addressService.deleteAddress(id);
            return new ResponseEntity<>("Address deleted successfully." ,HttpStatus.OK);   
        }
        catch(Exception e) { 
            return new ResponseEntity<>("Internal server error." , HttpStatus.INTERNAL_SERVER_ERROR) ; 
        } 
    }
} 