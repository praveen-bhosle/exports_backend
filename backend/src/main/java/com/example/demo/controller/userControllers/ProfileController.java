package com.example.demo.controller.userControllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.records.ProfileDTO;
import com.example.demo.service.ProfileService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/user/profile")
public class ProfileController  {

@Autowired 
ProfileService profileService ; 
 
 @PostMapping("")
 public ResponseEntity<?> createProfile( @Valid  @RequestBody ProfileDTO profileDTO ) {   
  
    try { 
    if(profileService.checkProfileExists()) { 
        return new ResponseEntity<>("User already has a profile" ,  HttpStatus.BAD_REQUEST) ;
    } 

    profileService.createProfile(profileDTO); 

    ProfileDTO createdProfile  = profileService.getProfile() ;

    return new ResponseEntity<>( createdProfile  , HttpStatus.CREATED) ;  
   } 
   catch( Exception e ) { 
    System.err.println(e.getMessage()); 
    return new ResponseEntity<>("Internal server error" ,  HttpStatus.INTERNAL_SERVER_ERROR) ; 
    }
 } 

 @GetMapping("")
 public ResponseEntity<?> getProfile( ) { 
    ProfileDTO profileDTO = profileService.getProfile() ; 
    return  new ResponseEntity<>(profileDTO , HttpStatus.OK ) ; 
  }

 @PutMapping("") 
 public ResponseEntity<?> editProfile( @Valid @RequestBody ProfileDTO profileDTO ) { 
    try { 
    UUID id = profileDTO.id() ; 
    if(id==null) {  return  new ResponseEntity<>("Id field is missing" , HttpStatus.BAD_REQUEST)  ;  }  
    if(!profileService.checkProfileBelongs(id)) {  return  new ResponseEntity<>("The profile sent for editing does not belongs to you." , HttpStatus.BAD_REQUEST)  ;  }  
    profileService.editProfile(profileDTO);
    ProfileDTO editedProfile  = profileService.getProfile() ;
    return  new ResponseEntity<>(editedProfile , HttpStatus.OK) ;  } 
    catch(Exception e) { 
        return new ResponseEntity<>("Internal server error." , HttpStatus.INTERNAL_SERVER_ERROR) ; 
    }
 }

 @DeleteMapping("")
 public ResponseEntity<?> deleteProfile( @Valid @RequestParam UUID id  ) { 
    try { 
        if(!profileService.checkProfileBelongs(id)) {  return  new ResponseEntity<>("The profile sent for editing does not belongs to you." , HttpStatus.BAD_REQUEST)  ;  }  
        profileService.deleteProfile(id);
        return new ResponseEntity<>( "profile deleted successfully." ,  HttpStatus.OK ) ; 
    }
    catch(Exception e ) { 
        return new ResponseEntity<>("Internal server error" , HttpStatus.INTERNAL_SERVER_ERROR) ;
    }
 }
}