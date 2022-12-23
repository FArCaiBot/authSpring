package com.farcai.security.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.farcai.security.exceptions.AppException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    public String generarToken(Authentication authentication) {
        String username = authentication.getName();
        Date fechaActual = new Date();
        Date fechaExpiration = new Date(fechaActual.getTime() + jwtExpirationInMs);

        String token = Jwts.builder().setSubject(username)
                .setIssuedAt(new Date()).setExpiration(fechaExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return token;
    }

    public String obtenerUsernameDelJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new AppException("Firma JWT no valida", HttpStatus.BAD_REQUEST);
        } catch (MalformedJwtException ex) {
            throw new AppException("Token JWT no valida", HttpStatus.BAD_REQUEST);
        } catch (ExpiredJwtException ex) {
            throw new AppException("Token JWT caducado", HttpStatus.BAD_REQUEST);
        } catch (UnsupportedJwtException ex) {
            throw new AppException("Token JWT no compatible", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException ex) {
            throw new AppException("La cadena de claims JWT esta vacía", HttpStatus.BAD_REQUEST);
        }
    }

}
