package com.airtribe.taskmaster.dto;

import java.time.Instant;
import java.util.List;

public record TeamResponse(
        Long id,
        String name,
        String description,
        UserResponse owner,
        List<UserResponse> members,
        Instant createdAt,
        Instant updatedAt
) {}
