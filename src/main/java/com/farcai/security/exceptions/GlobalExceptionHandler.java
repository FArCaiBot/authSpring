package com.farcai.security.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Control de excepciones de la aplicación en general
     * 
     * @param exception
     * @param webRequest
     * @return
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorDetalles> manejarAppException(AppException exception, WebRequest webRequest) {
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), exception.getMensaje(),
                webRequest.getDescription(false));
        return new ResponseEntity<ErrorDetalles>(errorDetalles, HttpStatus.BAD_REQUEST);
    }

    /**
     * Control de excepciones para recursos no encontrados Error 404
     * 
     * @param exception
     * @param webRequest
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotFoundException(ResourceNotFoundException exception,
            WebRequest webRequest) {
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<ErrorDetalles>(errorDetalles, HttpStatus.NOT_FOUND);

    }

    /**
     * Manejor global de error - Internal Server Error
     * 
     * @param exception
     * @param webRequest
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarGlobalException(Exception exception, WebRequest webRequest) {
        ErrorDetalles errorDetalles = new ErrorDetalles(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<ErrorDetalles>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nombreCampo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(nombreCampo, mensaje);
        });
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

}
