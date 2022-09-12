package com.project.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @GetMapping("/healthCheck")
    public ResponseEntity healthCheck(){
        return new ResponseEntity("success", HttpStatus.OK);
    }

}
