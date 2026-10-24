package com.airtribe.taskmaster.dto;

import java.time.Instant;

public record CommentResponse(
        Long id,
        String content,
        UserResponse author,
        Instant createdAt
) {}
