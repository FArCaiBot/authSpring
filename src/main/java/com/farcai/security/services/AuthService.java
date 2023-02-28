package com.farcai.security.services;

import java.util.Collections;
import java.util.Objects;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.farcai.security.dto.RegistroDTO;
import com.farcai.security.email.context.AccountVerificationEmailContext;
import com.farcai.security.email.service.EmailService;
import com.farcai.security.exceptions.AppException;
import com.farcai.security.model.Rol;
import com.farcai.security.model.SecureToken;
import com.farcai.security.model.Usuario;
import com.farcai.security.repository.RolRepository;
import com.farcai.security.repository.SecuretokenRepository;
import com.farcai.security.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    private SecuretokenRepository securetokenRepository;

    @Autowired
    private EmailService emailService;

    @Value("${site.base.url.https}")
    private String baseURL;

    public String registrarUsuario(RegistroDTO registroDTO) {
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
        usuario.setAccountVerified(false);

        Rol roles = rolRepository.findByNombre("ROLE_ADMIN").get();
        usuario.setRoles(Collections.singletonList(roles));

        usuarioRepository.save(usuario);
        sendRegistrationConfirmationEmail(usuario);

        return "Usuario Registrado exitosamente";

    }

    public void sendRegistrationConfirmationEmail(Usuario usuario) {
        SecureToken secureToken = secureTokenService.createSecureToken();
        secureToken.setUsuario(usuario);
        securetokenRepository.save(secureToken);

        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(usuario);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationURL(baseURL, secureToken.getToken());
        try {
            emailService.senEmail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyUser(String token) {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if (Objects.isNull(secureToken) || !(token.equals(secureToken.getToken())) || secureToken.isExpired()) {
            throw new RuntimeException("Token is not valid");
        }
        Usuario usuario = usuarioRepository.findById(secureToken.getUsuario().getId()).get();
        if (usuario == null) {
            return false;
        }
        usuario.setAccountVerified(true);
        usuarioRepository.save(usuario);
        secureTokenService.removeToken(secureToken);
        return true;
    }

}
