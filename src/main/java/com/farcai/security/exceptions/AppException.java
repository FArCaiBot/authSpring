package com.farcai.security.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private HttpStatus estado;
    private String mensaje;

    public AppException(String mensaje, HttpStatus estado) {
        super();
        this.estado = estado;
        this.mensaje = mensaje;
    }

}
