package com.vardhanreddy1706.URLEncoder.Service;

import com.vardhanreddy1706.URLEncoder.Models.Shorturl;
import com.vardhanreddy1706.URLEncoder.Repository.ShorturlRepository;
import org.springframework.stereotype.Service;

@Service
public class ShorturlService {

    private final ShorturlRepository shorturlRepository;

    public ShorturlService(ShorturlRepository shorturlRepository) {
        this.shorturlRepository = shorturlRepository;
    }

    public Shorturl getShorturl(String id) {
        return shorturlRepository.findById(id).orElseThrow(()-> new RuntimeException("hort URL not found for key: " + id));
    }
}
