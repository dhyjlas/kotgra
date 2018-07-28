package com.example.kotgra.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test2Controller {
    @RequestMapping("2")
    public String hello(){
        return "Hello World";
    }
}
