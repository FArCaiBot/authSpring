package com.farcai.security.services;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import com.farcai.security.model.SecureToken;
import com.farcai.security.repository.SecuretokenRepository;

@Service
public class DefaultSecureTokenService implements SecureTokenService {

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);
    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Value("${farcai.secure.token.validity}")
    private int tokenValidityInSeconds;

    @Autowired
    private SecuretokenRepository securetokenRepository;

    @Override
    public SecureToken createSecureToken() {
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII);
        SecureToken secureToken = new SecureToken();
        secureToken.setToken(tokenValue);
        secureToken.setExpireAt(LocalDateTime.now().plusSeconds(getTokenValidityInSeconds()));
        this.saveSecureToken(secureToken);
        return secureToken;
    }

    @Override
    public void saveSecureToken(SecureToken token) {
        securetokenRepository.save(token);
    }

    @Override
    public SecureToken findByToken(String token) {
        return securetokenRepository.findByToken(token);
    }

    @Override
    public void removeToken(SecureToken token) {
        securetokenRepository.delete(token);

    }

    @Override
    public void removeTokenByToken(String token) {
        securetokenRepository.removeByToken(token);

    }

    public int getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }

}
