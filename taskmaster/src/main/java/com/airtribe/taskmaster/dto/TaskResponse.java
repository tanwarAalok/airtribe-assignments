package com.airtribe.taskmaster.dto;

import com.airtribe.taskmaster.entity.TaskPriority;
import com.airtribe.taskmaster.entity.TaskStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDate dueDate,
        UserResponse reporter,
        UserResponse assignee,
        TeamResponse team,
        List<CommentResponse> comments,
        List<AttachmentResponse> attachments,
        Instant createdAt,
        Instant updatedAt
) {}
