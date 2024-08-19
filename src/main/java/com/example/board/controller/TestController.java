package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @CrossOrigin("http://localhost:3000/")
    @GetMapping("/")
    public String controllerTest() {
        return "data 준비중";
    }

    @GetMapping("/proxy-test")
    public String proxyTest() {
        return "api data 준비중";
    }
}
