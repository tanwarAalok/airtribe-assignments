package com.airtribe.taskmaster.dto;

import com.airtribe.taskmaster.entity.Role;

import java.time.Instant;
import java.util.Set;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        Set<Role> roles,
        Instant createdAt,
        Instant updatedAt
) {}
