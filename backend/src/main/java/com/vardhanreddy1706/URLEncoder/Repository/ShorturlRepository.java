package com.vardhanreddy1706.URLEncoder.Repository;

import com.vardhanreddy1706.URLEncoder.Models.Shorturl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface ShorturlRepository extends MongoRepository<Shorturl,String> {
    boolean existsByShortKey(String shortKey);

    Page<Shorturl> findByIsPrivateFalse(Pageable pageable);

    Optional<Shorturl> findByShortKey(String shortKey);

// @Query  → find the document whose short_key matches the argument
// @Update → increment click_count by exactly 1
// ?0      → first method argument: shortKey
// long    → number of documents updated

    @Query("{'short_key': ?0 }")
    @Update("{ '$inc' : {'click_count' : 1}}")  
    long incrementClickCount(String shortKey);
}


