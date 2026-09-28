package com.vardhanreddy1706.URLEncoder.Controller;

import com.vardhanreddy1706.URLEncoder.DTO.ShorturlResponse;
import com.vardhanreddy1706.URLEncoder.Models.Shorturl;

import com.vardhanreddy1706.URLEncoder.Service.ShorturlService;
import com.vardhanreddy1706.URLEncoder.DTO.CreateShortUrlRequest;
import jakarta.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

   @GetMapping("/short-urls/{id}")
public ResponseEntity<ShorturlResponse> getMyShortUrl(
        @PathVariable String id,
        Authentication authentication
) {
    Shorturl shorturl = shorturlService.getMyShortUrlById(
            id,
            authentication.getName()
    );

    String generatedShortUrl = buildShortUrl(
            shorturl.getShortKey()
    );

    return ResponseEntity.ok(
            ShorturlResponse.from(
                    shorturl,
                    generatedShortUrl
            )
    );
}

     @GetMapping("/short-urls/mine")
    public ResponseEntity<Page<ShorturlResponse>> getMyShortUrls(Authentication authentication,Pageable pageable){
        Page<ShorturlResponse> response =  shorturlService.getMyShorturls(authentication.getName(), pageable)
        .map(shorturl -> ShorturlResponse.from(shorturl, buildShortUrl(shorturl.getShortKey())));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/short-urls/{id}") 
    public ResponseEntity<Void> deleteUrl(@PathVariable String id,Authentication authentication ){

       shorturlService.deleteMyShorturl(id, authentication.getName());
       
       return ResponseEntity.noContent().build();
    }

   


    @PostMapping("/short-urls")
    public ResponseEntity<?> createShorturl(@Valid @RequestBody CreateShortUrlRequest req, BindingResult bindingResult, Authentication authentication ){

      if(bindingResult.hasErrors()){
        return ResponseEntity
        .badRequest()
        .body(bindingResult.getAllErrors());
      }

      Shorturl result = shorturlService.createShorturl(req.originalUrl(), req.expiresAt(),authentication.getName());

        
      String shortUrl = buildShortUrl(result.getShortKey());
      
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ShorturlResponse.from(result,shortUrl));
    }

    @GetMapping("/publicUrls")
    public ResponseEntity<Page<ShorturlResponse>> publicUrls(Pageable pageable){
      Page<ShorturlResponse> response=  shorturlService.getPublicUrls(pageable)
      .map(ShorturlResponse::from);
      
      return ResponseEntity.ok(response);
    }

}
