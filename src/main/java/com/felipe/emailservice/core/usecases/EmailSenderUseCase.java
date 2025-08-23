package com.felipe.emailservice.core.usecases;

public interface EmailSenderUseCase {
    void sendEmail(String destiny, String subject, String body);
}
