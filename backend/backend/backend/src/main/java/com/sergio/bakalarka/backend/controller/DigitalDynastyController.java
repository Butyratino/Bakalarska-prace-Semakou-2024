package com.sergio.bakalarka.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/digitaldynasty")
public class DigitalDynastyController {

    @GetMapping("/status")
    public String checkStatus() {
        return "Server is running";
    }
}
