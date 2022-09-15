package com.project.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommonController {
    private final Environment env;
    @GetMapping("/user-service/healthCheck")
    public String healthCheck(){

        return String.format("It's working in User Service on PORT %s",
                env.getProperty("local.server.port"));
    }

}
