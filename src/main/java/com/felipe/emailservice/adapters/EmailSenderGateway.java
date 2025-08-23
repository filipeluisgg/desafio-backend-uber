package com.felipe.emailservice.adapters;

public interface EmailSenderGateway {
    void sendEmail(String destiny, String subject, String body);
}
