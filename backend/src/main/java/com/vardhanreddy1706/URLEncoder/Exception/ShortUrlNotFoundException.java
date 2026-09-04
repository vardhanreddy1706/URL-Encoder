package com.vardhanreddy1706.URLEncoder.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShortUrlNotFoundException extends RuntimeException {

    public ShortUrlNotFoundException(String shortKey) {
        super("Short URL not found for key: " + shortKey);
    }
}
