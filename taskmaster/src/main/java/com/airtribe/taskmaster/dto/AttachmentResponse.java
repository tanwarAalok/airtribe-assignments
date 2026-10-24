package com.airtribe.taskmaster.dto;

import java.time.Instant;

public record AttachmentResponse(
        Long id,
        String fileName,
        String contentType,
        Long size,
        Instant createdAt
) {}
