package com.felipe.emailservice.application;

import com.felipe.emailservice.adapters.EmailSenderGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class EmailSenderServiceTest
{
    @Mock
    private EmailSenderGateway emailSenderGateway;

    private EmailSenderService emailSenderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emailSenderService = new EmailSenderService(emailSenderGateway);
    }

    @Test
    void shouldSendEmailSuccessfully() {
        String destiny = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        emailSenderService.sendEmail(destiny, subject, body);

        verify(emailSenderGateway, times(1)).sendEmail(destiny, subject, body);
    }
}
