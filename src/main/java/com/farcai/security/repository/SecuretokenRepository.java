package com.farcai.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farcai.security.model.SecureToken;

public interface SecuretokenRepository extends JpaRepository<SecureToken, Long> {
    SecureToken findByToken(final String token);

    Long removeByToken(String token);
}
