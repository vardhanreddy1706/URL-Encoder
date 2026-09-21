package com.vardhanreddy1706.URLEncoder.DTO;

import java.time.LocalDateTime;

import com.vardhanreddy1706.URLEncoder.Models.User;

public record RegisterResponse(
     String id,
        String name,
        String email,
        String role,
        LocalDateTime createdAt
) {

     public static RegisterResponse from(User user) {
        return new RegisterResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
    
}
