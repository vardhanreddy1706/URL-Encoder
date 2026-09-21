package com.vardhanreddy1706.URLEncoder.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest (

    @NotBlank(message = "Email is Required") 
    @Email(message = "Email format is invalid") 
    String email,

    @NotBlank(message = "password is required") 
    String password
    
){}
