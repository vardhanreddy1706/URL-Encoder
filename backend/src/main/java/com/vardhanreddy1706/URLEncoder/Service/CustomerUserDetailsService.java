package com.vardhanreddy1706.URLEncoder.Service;

import java.util.Locale;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vardhanreddy1706.URLEncoder.Models.User;
import com.vardhanreddy1706.URLEncoder.Repository.UserRepository;

@Service 
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomerUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    
    //to load all user details from database
    @Override 
    public UserDetails loadUserByUsername(String email){
        String normalizedemail = email.trim().toLowerCase(Locale.ROOT);
        User user = userRepository.findByEmail(normalizedemail)
        .orElseThrow(() -> new UsernameNotFoundException("invalid email or password"));

        //We use Spring Security’s UserDetails as the authenticated representation while keeping your MongoDB User as the database entity:
        return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .authorities(user.getRole())
        .build();
    }
}
