package com.vardhanreddy1706.URLEncoder.Service;

import com.vardhanreddy1706.URLEncoder.Models.Shorturl;
import com.vardhanreddy1706.URLEncoder.Repository.ShorturlRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

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

    public Shorturl getShorturl(String id) {
        return shorturlRepository.findById(id).orElseThrow(()-> new RuntimeException("Short URL not found for id: " + id));
    }

    public Shorturl createShorturl(String originalUrl) {
      return shorturlRepository.findByOriginalUrl(originalUrl)
        .orElseGet(()->{
              String shortKey = generateUniqueShortKey();
        Shorturl shorturl = new Shorturl(shortKey, originalUrl, null, false, null);
        return shorturlRepository.save(shorturl);
        });
    }

    private String generateUniqueShortKey() {
        String shortKey;

        do {
            StringBuilder builder = new StringBuilder(KEY_LENGTH);
            for (int i = 0; i < KEY_LENGTH; i++) {
                builder.append(KEY_CHARACTERS.charAt(RANDOM.nextInt(KEY_CHARACTERS.length())));
            }
            shortKey = builder.toString();
        } while (shorturlRepository.existsByShortKey(shortKey));

        return shortKey;
    }

    public Page<Shorturl> getPublicUrls(Pageable pageable){
        return shorturlRepository.findByIsPrivateFalse(pageable);
    }
}
