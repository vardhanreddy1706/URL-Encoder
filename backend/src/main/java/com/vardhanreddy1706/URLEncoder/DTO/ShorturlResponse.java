package com.vardhanreddy1706.URLEncoder.DTO;


import com.vardhanreddy1706.URLEncoder.Models.Shorturl;

import java.time.LocalDateTime;

public record ShorturlResponse(
        String id,
        String shortKey,
        String originalUrl,
        LocalDateTime createdAt,
        Long clickCount
) {
    public static ShorturlResponse from(Shorturl shorturl) {
        return new ShorturlResponse(
                shorturl.getId(),
                shorturl.getShortKey(),
                shorturl.getOriginalUrl(),
                shorturl.getCreatedAt(),
                shorturl.getClickCount()
        );
    }
}

