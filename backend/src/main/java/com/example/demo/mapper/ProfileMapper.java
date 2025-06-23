package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.model.Profile;
import com.example.demo.records.ProfileDTO;

@Mapper(componentModel="spring")
public interface ProfileMapper {
    ProfileDTO toProfileDTO( Profile profile )  ;
    
    @Mapping(target="image" ,ignore=true)
    Profile    toProfile( ProfileDTO profileDTO) ; 
}
