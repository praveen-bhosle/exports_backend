package com.example.demo.records;

import java.time.LocalDateTime;

import com.example.demo.model.Profile;

public record UserDTO (String username ,  String email ,String phone ,  Profile profile , LocalDateTime createdAt ,  LocalDateTime updatedAt ) {}