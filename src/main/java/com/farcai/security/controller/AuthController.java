package com.farcai.security.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farcai.security.dto.LoginDTO;
import com.farcai.security.dto.RegistroDTO;
import com.farcai.security.security.JWTAuthResponseDTO;
import com.farcai.security.security.JwtTokenProvider;
import com.farcai.security.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // obtenemos el token del jwtTokenProvider
        String token = jwtTokenProvider.generarToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponseDTO(token, "Bearer"));
    }

    @PostMapping("signup")
    public ResponseEntity<?> registrarUsuario(@RequestBody @Valid RegistroDTO registroDTO) {
        return new ResponseEntity<>(authService.registrarUsuario(registroDTO), HttpStatus.OK);
    }

    @GetMapping("verify")
    public ResponseEntity<Boolean> verifyUser(@RequestParam(required = true, name = "token") String token) {
        return new ResponseEntity<Boolean>(authService.verifyUser(token), HttpStatus.OK);

    }

}
