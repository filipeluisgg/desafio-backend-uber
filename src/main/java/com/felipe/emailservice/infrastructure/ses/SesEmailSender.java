package com.felipe.emailservice.infrastructure.ses;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.felipe.emailservice.adapters.EmailSenderGateway;
import com.felipe.emailservice.core.exceptions.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SesEmailSender implements EmailSenderGateway
{
    @Value("${my.email}")
    private String myEmail;

    private final AmazonSimpleEmailService sesClient;

    @Autowired
    public SesEmailSender(AmazonSimpleEmailService sesClient) {
        this.sesClient = sesClient;
    }

    @Override
    public void sendEmail(String destiny, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
                .withSource(myEmail)
                .withDestination(new Destination().withToAddresses(destiny))
                .withMessage(new Message()
                        .withSubject(new Content(subject))
                        .withBody(new Body().withText(new Content(body)))
                );

        try {
            sesClient.sendEmail(request);
        } catch (AmazonServiceException ex) {
            throw new EmailServiceException("Error: Mail sending failed", ex);
        }
    }
}
