package com.vardhanreddy1706.URLEncoder.Controller;

import com.vardhanreddy1706.URLEncoder.Models.Shorturl;
import com.vardhanreddy1706.URLEncoder.Repository.ShorturlRepository;
import com.vardhanreddy1706.URLEncoder.Service.ShorturlService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class ShorturlController {

    @Autowired
    private ShorturlService shorturlService;
    @Autowired
    private ShorturlRepository shorturlRepository;

    @GetMapping("/")
    public String hello(){
        return "hello world";
    }

    @GetMapping("/getUrl/{id}")
    public Shorturl getUrls(@PathVariable("id") String id){
        return shorturlService.getShorturl(id);
    }

    @PostMapping("/short_urls")
    public Shorturl createShorturl(@Valid @ModelAttribute Shorturl shorturl , BindingResult bindingResult){

    }

}
