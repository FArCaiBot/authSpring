package com.farcai.security.services;

import com.farcai.security.model.SecureToken;

public interface SecureTokenService {

    SecureToken createSecureToken();

    void saveSecureToken(final SecureToken token);

    SecureToken findByToken(final String token);

    void removeToken(final SecureToken token);

    void removeTokenByToken(final String token);
}
