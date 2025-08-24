package com.felipe.emailservice.application;

import com.felipe.emailservice.adapters.EmailSenderGateway;
import com.felipe.emailservice.core.usecases.EmailSenderUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService implements EmailSenderUseCase
{
    private final EmailSenderGateway emailSenderGateway; //SesEmailSender obj will be injected here.

    @Autowired
    public EmailSenderService(EmailSenderGateway emailSenderGateway) {
        this.emailSenderGateway = emailSenderGateway;
    }

    @Override
    public void sendEmail(String destiny, String subject, String body) {
        emailSenderGateway.sendEmail(destiny, subject, body);
    }
}
