package com.felipe.emailservice.infrastructure.ses;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.felipe.emailservice.core.exceptions.EmailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SesEmailSenderTest
{
    @Mock
    private AmazonSimpleEmailService sesClient;

    private SesEmailSender sesEmailSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sesEmailSender = new SesEmailSender(sesClient);
        ReflectionTestUtils.setField(sesEmailSender, "myEmail", "sender@example.com");
    }

    @Test
    void shouldSendEmailSuccessfully() {
        String destiny = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        when(sesClient.sendEmail(any(SendEmailRequest.class))).thenReturn(new SendEmailResult());

        assertDoesNotThrow(() -> sesEmailSender.sendEmail(destiny, subject, body));

        verify(sesClient, times(1)).sendEmail(any(SendEmailRequest.class));
    }

    @Test
    void shouldThrowEmailServiceException_WhenAmazonServiceExceptionOccurs()
    {
        String destiny = "test@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        when(sesClient.sendEmail(any(SendEmailRequest.class))).thenThrow(new AmazonServiceException("AWS Error"));

        EmailServiceException exception = assertThrows(EmailServiceException.class, () -> {
            sesEmailSender.sendEmail(destiny, subject, body);
        });

        assertEquals("Error: Mail sending failed", exception.getMessage());
        assertNotNull(exception.getCause());
        assertTrue(exception.getCause() instanceof AmazonServiceException);

        verify(sesClient, times(1)).sendEmail(any(SendEmailRequest.class));
    }
}
