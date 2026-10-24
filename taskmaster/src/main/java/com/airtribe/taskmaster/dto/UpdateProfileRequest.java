package com.airtribe.taskmaster.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileRequest(
        @NotBlank(message = "Full name is required")
        String fullName
) {}
