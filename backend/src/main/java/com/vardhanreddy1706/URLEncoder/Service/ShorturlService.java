package com.vardhanreddy1706.URLEncoder.Service;

import com.vardhanreddy1706.URLEncoder.Exception.ShortUrlExpiredException;
import com.vardhanreddy1706.URLEncoder.Exception.ShortUrlNotFoundException;
import com.vardhanreddy1706.URLEncoder.Models.Shorturl;
import com.vardhanreddy1706.URLEncoder.Repository.ShorturlRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ShorturlService {

    private static final String KEY_CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int KEY_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final ShorturlRepository shorturlRepository;

    public ShorturlService(ShorturlRepository shorturlRepository) {
        this.shorturlRepository = shorturlRepository;
    }

    //get shortUrl with id - mongo _id
    public Shorturl getShorturl(String id) {
        return shorturlRepository.findById(id).orElseThrow(()-> new RuntimeException("Short URL not found for id: " + id));
    }

    //saving the URL details in Database
    public Shorturl createShorturl(String originalUrl, LocalDateTime expiresAt) {

      
         String shortKey = generateUniqueShortKey();
        Shorturl shorturl = new Shorturl(shortKey, originalUrl, null, false, expiresAt);
       
         return shorturlRepository.save(shorturl);
    }

    //generate always new shortkey for same url submitted multiple times.
    private String generateUniqueShortKey() {
        String shortKey;

        do {
            shortKey = generateRandomShortkey();
        } while (shorturlRepository.existsByShortKey(shortKey));

        return shortKey;
    }

    //generating 6 - digit random key
    public String generateRandomShortkey(){

          String shortKey;

            StringBuilder builder = new StringBuilder(KEY_LENGTH);
            for (int i = 0; i < KEY_LENGTH; i++) {
                builder.append(KEY_CHARACTERS.charAt(RANDOM.nextInt(KEY_CHARACTERS.length())));
            }
            shortKey = builder.toString();

            return shortKey;
    }

    //getting all public urls
    public Page<Shorturl> getPublicUrls(Pageable pageable){
        return shorturlRepository.findByIsPrivateFalse(pageable);
    }

    //finding shortkey
    public Shorturl getByShortKey(String shortKey){
        return shorturlRepository
        .findByShortKey(shortKey)
        .orElseThrow(()-> new ShortUrlNotFoundException(shortKey));
    }

    //updating click count
    public Shorturl getByShortKeyAndIncrementClickCount(String shortKey){
        Shorturl shorturl = getByShortKey(shortKey);
        LocalDateTime expiresAt = shorturl.getExpiresAt();

        if(expiresAt != null && !expiresAt.isAfter(LocalDateTime.now())){
            throw new ShortUrlExpiredException(shortKey);
        }
        shorturlRepository.incrementClickCount(shortKey);
        return shorturl;
    }
}
