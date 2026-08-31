package com.vardhanreddy1706.URLEncoder.Models;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "url")
public class Shorturl {

    @Id
    private String id;

    @NotBlank(message = "Short key is required")
    @Indexed(unique = true)
    @Field("short_key")
    private String shortKey;

    @NotBlank(message = "Original URL is required")
    @Field("original_url")
    private String originalUrl;

    @DBRef
    @Field("created_by")
    private User createdBy;

    @Field("is_private")
    private boolean isPrivate = false;

    @Field("created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Field("expires_at")
    private LocalDateTime expiresAt;

    @Field("click_count")
    private Long clickCount = 0L;

    public Shorturl() {}

    public Shorturl(String shortKey, String originalUrl, User createdBy, boolean isPrivate, LocalDateTime expiresAt) {
        this.shortKey = shortKey;
        this.originalUrl = originalUrl;
        this.createdBy = createdBy;
        this.isPrivate = isPrivate;
        this.expiresAt = expiresAt;
        this.createdAt = LocalDateTime.now();
        this.clickCount = 0L;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getShortKey() { return shortKey; }
    public void setShortKey(String shortKey) { this.shortKey = shortKey; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public boolean isPrivate() { return isPrivate; }
    public void setPrivate(boolean aPrivate) { isPrivate = aPrivate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public Long getClickCount() { return clickCount; }
    public void setClickCount(Long clickCount) { this.clickCount = clickCount; }
}