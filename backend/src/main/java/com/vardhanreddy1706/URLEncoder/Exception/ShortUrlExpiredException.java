package com.vardhanreddy1706.URLEncoder.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE) 
public class ShortUrlExpiredException extends RuntimeException {
    
    public ShortUrlExpiredException (String shortKey){
        super("short URL has expired" + shortKey);
    }
}
