package com.example.airtel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airtel")
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<Object> test(){
        return ResponseEntity.ok("Reussit");
    }
}
