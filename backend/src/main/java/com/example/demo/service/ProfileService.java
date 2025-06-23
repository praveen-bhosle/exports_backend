package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ProfileMapper;
import com.example.demo.model.Profile;
import com.example.demo.model.User;
import com.example.demo.records.ProfileDTO;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.GetUsername;


@Service
public class ProfileService {

    @Autowired 

    private UserRepository userRepository ; 

    @Autowired
    private ProfileRepository profileRepository ;

    @Autowired
    private ProfileMapper profileMapper ; 

    public ProfileDTO  getProfile() { 
        Profile profile = userRepository.findByUsername(GetUsername.getUsername()).getProfile() ; 
        return  profileMapper.toProfileDTO(profile) ;  
    }

    public boolean  checkProfileExists() { 
     String username = GetUsername.getUsername(); 
     User user = userRepository.findByUsername(username) ;
     Profile userProfile = user.getProfile() ;
     return userProfile != null ;
    }
 
    public boolean  checkProfileBelongs( Long id  ) {   
        String username = GetUsername.getUsername() ; 
        return id.equals(userRepository.findByUsername(username).getProfile().getId() ) ;
    }

    public void  createProfile( ProfileDTO profile ) {  
        String username = GetUsername.getUsername() ; 
        User user = userRepository.findByUsername(username) ; 
        Profile userProfile =  profileMapper.toProfile(profile) ; 
        user.setProfile(userProfile); 
        userRepository.save(user) ; 
    }

    public void editProfile( ProfileDTO profile) { 
        Profile userProfile = profileMapper.toProfile(profile) ; 
        profileRepository.save(userProfile) ; 
    }

    public void deleteProfile(Long id ){ 
        profileRepository.deleteById(id);  
    }
}
