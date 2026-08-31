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


@RestController
@RequestMapping("api/v1")
public class ShorturlController {

    @Autowired
    private ShorturlService shorturlService;


    @GetMapping("/")
    public String hello(){
        return "hello world";
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
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ShorturlResponse.from(result));
    }

}
