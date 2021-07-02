package com.travel.blogservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blog")
public class TestController {

    @GetMapping("/blog")
    public String getAllBlog(){
        return "This is blog";
    }
}
