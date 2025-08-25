package com.felipe.emailservice.core.dto;

import jakarta.validation.constraints.NotBlank;

public record EmailRequest (
    @NotBlank(message = "Destiny email is required")
    String destiny,

    @NotBlank(message = "Subject is required")
    String subject,

    @NotBlank(message = "Body is required")
    String body
) { }
