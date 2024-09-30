package com.example.indistockpulse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LandingPageController {

    public String getRootPage() {
        return "index";
    }
}
