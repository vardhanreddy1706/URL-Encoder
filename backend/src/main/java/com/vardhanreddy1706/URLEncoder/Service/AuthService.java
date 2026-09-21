package com.vardhanreddy1706.URLEncoder.Service;



import java.util.Locale;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vardhanreddy1706.URLEncoder.DTO.RegisterRequest;
import com.vardhanreddy1706.URLEncoder.DTO.RegisterResponse;
import com.vardhanreddy1706.URLEncoder.Exception.EmailAlreadyExistsException;
import com.vardhanreddy1706.URLEncoder.Models.User;
import com.vardhanreddy1706.URLEncoder.Repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegisterRequest request) {
        String normalizedEmail = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException(
                    "An account with this email already exists"
            );
        }

        User user = new User(
                request.name().trim(),
                normalizedEmail,
                passwordEncoder.encode(request.password()),
                "ROLE_USER"
        );

        try {
            User savedUser = userRepository.save(user);
            return RegisterResponse.from(savedUser);

        } catch (DuplicateKeyException exception) {
            throw new EmailAlreadyExistsException(
                    "An account with this email already exists"
            );
        }
    }
}