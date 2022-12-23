package com.farcai.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class InitController {
    @GetMapping("admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Ruta de acceso solo para administradores!!");
    }

    @GetMapping("user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> usuario() {
        return ResponseEntity.ok("Ruta solo para usuarios comunes");
    }

}
