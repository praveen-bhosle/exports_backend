package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.AddressMapper;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.records.AddressDTO;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.GetUsername;

@Service
public class AddressService { 
@Autowired
private AddressRepository addressRepository ;  
@Autowired 
private AddressMapper addressMapper ; 
@Autowired 
private UserRepository userRepository ; 


public  List<AddressDTO>  getAddresses() { 
    String username =   GetUsername.getUsername() ;
    List<AddressDTO> addresses = new ArrayList<>() ; 
    List<Address> addr = addressRepository.findAllByUserUsername(username) ; 
    for( Address address : addr ) { 
        addresses.add( addressMapper.toAddressDTO( address)) ;  
    } 
    return  addresses ; 
}

 
public  void   createAddress( AddressDTO addressDTO ) { 
    String username = GetUsername.getUsername() ; 
    User user = userRepository.findByUsername(username) ;     
    Address address = addressMapper.toAddress(addressDTO) ; 
    address.setUser(user);
    addressRepository.save(address) ; 
}

public void   editAddress(AddressDTO addressDTO) {  
   // Long id = addressDTO.id() ; 
    String username = GetUsername.getUsername() ;  
    User user = userRepository.findByUsername(username) ;   
    Address newAddress  = addressMapper.toAddress(addressDTO) ;  
   // newAddress.setId(id) ; 
    newAddress.setUser(user);
    addressRepository.save(newAddress) ; 
}

public  void  deleteAddress( UUID id  )  {  

    Address address = addressRepository.findById(id).orElse(null)  ;  
    if(address!= null ) addressRepository.delete(address);  
}

public boolean  validate(UUID id ) {    
    String username =  GetUsername.getUsername() ; 
    Address address  = addressRepository.findById(id).orElse(null) ; 
    return  address != null && address.getUser().getUsername().equals(username)  ;
}

}