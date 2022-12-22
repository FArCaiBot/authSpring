package com.farcai.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class InitController {
    @GetMapping
    public ResponseEntity<String> saludo() {
        return ResponseEntity.ok("HOla mundo!!");
    }

}
