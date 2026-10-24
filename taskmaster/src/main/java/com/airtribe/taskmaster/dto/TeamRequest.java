package com.airtribe.taskmaster.dto;

import jakarta.validation.constraints.NotBlank;

public record TeamRequest(
        @NotBlank(message = "Team name is required")
        String name,
        String description
) {}
