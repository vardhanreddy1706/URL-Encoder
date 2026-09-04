package com.vardhanreddy1706.URLEncoder.Repository;

import com.vardhanreddy1706.URLEncoder.Models.Shorturl;

import java.util.Optional;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShorturlRepository extends MongoRepository<Shorturl,String> {
    boolean existsByShortKey(String shortKey);

    Optional<Shorturl> findByOriginalUrl(String originalUrl);

    Page<ShorturlResponse> findByIsPrivateFalse(Pageable pageable);
}


