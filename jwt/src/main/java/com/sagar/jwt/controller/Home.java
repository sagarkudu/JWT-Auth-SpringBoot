package com.sagar.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {
    
    @GetMapping("/welcome")
    public String welcome(){
        String text = "This is private page";
        text += "this page is not allowed to the unauthenticated users";
        return text;
    }
}
