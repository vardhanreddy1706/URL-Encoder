package com.vardhanreddy1706.URLEncoder.Controller;

import com.vardhanreddy1706.URLEncoder.DTO.ShorturlResponse;
import com.vardhanreddy1706.URLEncoder.Models.Shorturl;
import com.vardhanreddy1706.URLEncoder.Repository.ShorturlRepository;
import com.vardhanreddy1706.URLEncoder.Service.ShorturlService;
import com.vardhanreddy1706.URLEncoder.DTO.CreateShortUrlRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@RestController
@RequestMapping("api/v1")
public class ShorturlController {

    @Autowired
    private ShorturlService shorturlService;


    @GetMapping("/")
    public String hello(){
        return "hello world";
    }


    //helper method
    private String buildShortUrl(String shortKey){
      return ServletUriComponentsBuilder
      .fromCurrentContextPath()
      .path("/{shortKey}")
      .buildAndExpand(shortKey)
      .toUriString();
    }

    @GetMapping("/getUrl/{id}")
    public Shorturl getUrls(@PathVariable("id") String id){
        return shorturlService.getShorturl(id);
    }

    @PostMapping("/short-urls")
    public ResponseEntity<?> createShorturl(@Valid @RequestBody CreateShortUrlRequest req, BindingResult bindingResult ){

      if(bindingResult.hasErrors()){
        return ResponseEntity
        .badRequest()
        .body(bindingResult.getAllErrors());
      }

      Shorturl result = shorturlService.createShorturl(req.originalUrl());

        
      String shortUrl = buildShortUrl(result.getShortKey());
      
        return ResponseEntity
        .status(URI.create(shortUrl))
        .body(ShorturlResponse.from(result,shortUrl));
    }

    @GetMapping("/publicUrls")
    public ResponseEntity<Page<ShorturlResponse>> publicUrls(Pageable pageable){
      Page<ShorturlResponse> response=  shorturlService.getPublicUrls(pageable)
      .map(ShorturlResponse::from);
        return ResponseEntity.ok(response);
    }

}
