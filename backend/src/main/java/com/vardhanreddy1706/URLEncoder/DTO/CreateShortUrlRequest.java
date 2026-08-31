package com.vardhanreddy1706.URLEncoder.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateShortUrlRequest(
        @NotBlank(message = "Original URL is required")
        @Pattern(
                regexp = "^https?://.+$",
                message = "Original URL must start with http:// or https://"
        )
        String originalUrl
) {
}
