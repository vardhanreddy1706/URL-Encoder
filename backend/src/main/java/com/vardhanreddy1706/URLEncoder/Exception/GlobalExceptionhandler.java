package com.vardhanreddy1706.URLEncoder.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionhandler {

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(
            AuthenticationException exception
    ) {

        System.out.println("Handler called: "+ exception.getClass().getSimpleName());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                "Invalid email or password"
        );

        problem.setTitle("Authentication failed");

        return problem;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
public ProblemDetail handleEmailAlreadyExists(
        EmailAlreadyExistsException exception
) {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.CONFLICT,
            exception.getMessage()
    );

    problem.setTitle("Email already registered");

    return problem;
}
}