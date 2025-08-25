package com.felipe.emailservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.felipe.emailservice.application.EmailSenderService;
import com.felipe.emailservice.core.dto.EmailRequest;
import com.felipe.emailservice.core.exceptions.EmailServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmailSenderController.class)
class EmailSenderControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailSenderService emailSenderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnOk_WhenEmailIsSentSuccessfully() throws Exception {
        EmailRequest emailRequest = new EmailRequest("test@example.com", "Test Subject", "Test Body");
        String emailRequestJson = objectMapper.writeValueAsString(emailRequest);

        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Email sent successfully!")
        );

        verify(emailSenderService).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void shouldReturnInternalServerError_WhenEmailServiceThrowsException() throws Exception {
        EmailRequest emailRequest = new EmailRequest("test@example.com", "Test Subject", "Test Body");
        String emailRequestJson = objectMapper.writeValueAsString(emailRequest);

        doThrow(new EmailServiceException("Error: Mail sending failed"))
                .when(emailSenderService).sendEmail(anyString(), anyString(), anyString());

        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailRequestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error: Mail sending failed.")
        );

        verify(emailSenderService).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void shouldReturnBadRequest_WhenEmailRequestIsInvalid() throws Exception {
        String invalidEmailRequestJson = "{\"destiny\":\"\", \"subject\":\"Test Subject\", \"body\":\"Test Body\"}";

        mockMvc.perform(post("/api/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidEmailRequestJson))
                .andExpect(status().isBadRequest()
        );

        verify(emailSenderService, never()).sendEmail(anyString(), anyString(), anyString());
    }
}
