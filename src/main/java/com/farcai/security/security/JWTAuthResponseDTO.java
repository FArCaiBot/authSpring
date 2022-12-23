package com.farcai.security.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JWTAuthResponseDTO {
    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";

}
