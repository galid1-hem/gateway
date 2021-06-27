package com.hem.gateway.controller;

import com.hem.gateway.controller.form.EmailAndPassword;
import com.hem.gateway.controller.form.HEMJTK;
import com.hem.gateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenIssuer {

    @Autowired
    AuthService authService;

    @GetMapping("/")
    public String test() {
        return "home page?";
    }

    @PostMapping(value="/login", consumes = "application/json", produces = "application/json")
    public HEMJTK login(@RequestBody EmailAndPassword credential) {
        String token = authService.issueToken(credential.getEmail(), credential.getPassword());
        return new HEMJTK(token,"test@email.com");
    }

    @PostMapping(value="/signup", produces = "application/json")
    public HEMJTK signup() {
        String token = authService.issueToken("test@email.com", "1234");
        return new HEMJTK(token, "test@email.com");
    }
}
