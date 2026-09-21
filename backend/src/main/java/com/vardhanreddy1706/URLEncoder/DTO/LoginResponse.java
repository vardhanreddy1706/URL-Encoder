package com.vardhanreddy1706.URLEncoder.DTO;



public record LoginResponse(
     String accessToken,
        String tokenType,
        long expiresIn
) {
    
}
