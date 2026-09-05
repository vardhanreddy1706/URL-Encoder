package com.vardhanreddy1706.URLEncoder.DTO;


import com.vardhanreddy1706.URLEncoder.Models.Shorturl;

import java.time.LocalDateTime;

public record ShorturlResponse(
        String id,
        String shortKey,
         String shortUrl,
        String originalUrl,
        LocalDateTime createdAt,
        Long clickCount
) {
    public static ShorturlResponse from(Shorturl shorturl,   String shortUrl) {
        return new ShorturlResponse(
                shorturl.getId(),
                shorturl.getShortKey(),
                shortUrl,
                shorturl.getOriginalUrl(),
                shorturl.getCreatedAt(),
                shorturl.getClickCount()
        );
    }
    
}

