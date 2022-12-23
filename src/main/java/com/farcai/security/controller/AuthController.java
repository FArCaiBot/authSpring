package com.farcai.security.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farcai.security.dto.LoginDTO;
import com.farcai.security.dto.RegistroDTO;
import com.farcai.security.exceptions.AppException;
import com.farcai.security.model.Rol;
import com.farcai.security.model.Usuario;
import com.farcai.security.repository.RolRepository;
import com.farcai.security.repository.UsuarioRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("Ha iniciado sesión con éxito", HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO) {
        if (usuarioRepository.existsByUsername(registroDTO.getUsername())) {
            throw new AppException("El nombre de usuario ya ha sido ocupado", HttpStatus.BAD_REQUEST);
        }

        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            throw new AppException("El email proporcionado ya se encuentra registrado", HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Rol roles = rolRepository.findByNombre("ROLE_ADMIN").get();
        usuario.setRoles(Collections.singletonList(roles));

        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario registrado exitosamente");

    }

}
