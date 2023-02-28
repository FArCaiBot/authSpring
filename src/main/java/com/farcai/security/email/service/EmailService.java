package com.farcai.security.email.service;

import javax.mail.MessagingException;

import com.farcai.security.email.context.AbstractEmailContext;

public interface EmailService {

    void senEmail(final AbstractEmailContext email) throws MessagingException;
}
