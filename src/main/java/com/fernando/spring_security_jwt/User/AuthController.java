package com.fernando.spring_security_jwt.User;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {
    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin";
    }
}
