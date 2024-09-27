package com.example.indistockpulse.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @GetMapping("/hello")
    public String helloIndiStockPulse() {
        return datasourceUrl;
    }
}
