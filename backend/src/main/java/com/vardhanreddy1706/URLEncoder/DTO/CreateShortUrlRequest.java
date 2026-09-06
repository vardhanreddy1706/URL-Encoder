package com.vardhanreddy1706.URLEncoder.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateShortUrlRequest(
        @NotBlank(message = "Original URL is required")
        @Pattern(
                regexp = "^https?://.+$",
                message = "Original URL must start with http:// or https://"
        )
        String originalUrl,

         @Future(message = "Expiration time must be in the future")
         @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss") 
        LocalDateTime expiresAt
) {
}
