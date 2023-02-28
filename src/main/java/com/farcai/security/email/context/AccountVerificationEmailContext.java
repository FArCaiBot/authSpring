package com.farcai.security.email.context;

import org.springframework.web.util.UriComponentsBuilder;

import com.farcai.security.model.Usuario;

public class AccountVerificationEmailContext extends AbstractEmailContext {
    private String token;

    @Override
    public <T> void init(T context) {
        Usuario usuario = (Usuario) context;
        put("nombre", usuario.getNombre());
        setTemplateLocation("emails/email-verification");
        setSubject("Complete your registration");
        setFrom("flavio.armijos@espoch.edu.ec");
        setTo(usuario.getEmail());
    }

    public void setToken(String token) {
        this.token = token;
        put("token", token);
    }

    public void buildVerificationURL(final String baseURL, final String token) {
        final String url = UriComponentsBuilder.fromHttpUrl(baseURL)
                .path("/account/verify").queryParam("token", token).toUriString();
        put("verificationURL", url);
    }

}
