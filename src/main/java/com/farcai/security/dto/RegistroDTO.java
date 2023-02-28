package com.farcai.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.farcai.security.validators.ValidPassword;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroDTO {
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String username;
    @Email(message = "Dirección de email no válida")
    private String email;

    @ValidPassword
    private String password;
}
