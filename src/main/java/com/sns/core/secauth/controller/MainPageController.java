package com.sns.core.secauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPageController {

    @GetMapping("/greet")
    public String main(Authentication a) {
        return "Hello, " + a.getName();
    }
}