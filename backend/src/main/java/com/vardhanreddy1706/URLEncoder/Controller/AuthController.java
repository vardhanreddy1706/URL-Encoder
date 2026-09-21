package com.vardhanreddy1706.URLEncoder.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vardhanreddy1706.URLEncoder.DTO.LoginRequest;
import com.vardhanreddy1706.URLEncoder.DTO.LoginResponse;
import com.vardhanreddy1706.URLEncoder.DTO.RegisterRequest;
import com.vardhanreddy1706.URLEncoder.DTO.RegisterResponse;
import com.vardhanreddy1706.URLEncoder.Service.AuthService;
import com.vardhanreddy1706.URLEncoder.Service.JwtTokenService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("api/v1/auth") 
public class AuthController {
    
    private final AuthenticationManager authenticationmanager;
    private final JwtTokenService jwtTokenService;
    private final AuthService authService;


    public AuthController(AuthenticationManager authenticationmanager, JwtTokenService jwtTokenService,AuthService authService){
          this.authenticationmanager = authenticationmanager;
          this.jwtTokenService = jwtTokenService;
          this.authService = authService;
    }


    @PostMapping("/login") 
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
         
        Authentication authentication  = authenticationmanager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(
            request.email(),
            request.password()
        ));

         String accessToken =
            jwtTokenService.generateToken(authentication);


    LoginResponse response = new LoginResponse(
            accessToken,
            "Bearer",
            jwtTokenService.getAccessTokenExpirationSeconds()
    );

    return ResponseEntity.ok(response);
        
      
    }

    @PostMapping("/register") 
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        RegisterResponse response = authService.register(registerRequest);

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);

    }
}
