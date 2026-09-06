package com.vardhanreddy1706.URLEncoder.Controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vardhanreddy1706.URLEncoder.Models.Shorturl;
import com.vardhanreddy1706.URLEncoder.Service.ShorturlService;

@RestController 
public class RedirectController {
    
    private ShorturlService shorturlservice;

    public RedirectController(ShorturlService shorturlservice){
        this.shorturlservice=shorturlservice;
    }

    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirect(@PathVariable String shortKey){

        Shorturl shorturl = shorturlservice.getByShortKeyAndIncrementClickCount(shortKey);

        return ResponseEntity
        .status(HttpStatus.FOUND)
        .location(URI.create(shorturl.getOriginalUrl()))
        .build();
        

    } 
    
}
