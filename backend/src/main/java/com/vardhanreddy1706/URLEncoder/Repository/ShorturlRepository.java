package com.vardhanreddy1706.URLEncoder.Repository;

import com.vardhanreddy1706.URLEncoder.Models.Shorturl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ShorturlRepository extends MongoRepository<Shorturl,String> {
    Optional<Shorturl> findById(String shortKey);

    Optional<Shorturl> findAllUrls(String url);

    List<Shorturl> findPublicShorturls();
}


