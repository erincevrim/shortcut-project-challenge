package com.eei.urlshortener.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/")
@AllArgsConstructor
public class MainController {
    @GetMapping(value = "/health-check")
    public String health()  {
        return "URL Shortener Application Running...";
    }
}
