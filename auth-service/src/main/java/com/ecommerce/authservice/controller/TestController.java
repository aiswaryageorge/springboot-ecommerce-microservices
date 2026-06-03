package com.ecommerce.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test() {

        return "Protected API Accessed";
    }
    @GetMapping("/admin")
    public String admin() {
        return "Admin Access Granted";
    }
    @GetMapping("/user")
    public String user() {
        return "User Access Granted";
    }
}
