package com.sns.core.secauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/read")
    @PreAuthorize("hasAuthority('READ')")
    public String readAccess() {
        return "User read content.";
    }
    @GetMapping("/write")
    @PreAuthorize("hasAuthority('write')")
    public String writeAccess() {
        return "User write content.";
    }

}