package com.airtribe.taskmaster.dto;

import com.airtribe.taskmaster.entity.TaskPriority;
import com.airtribe.taskmaster.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank(message = "Title is required")
        String title,
        String description,
        LocalDate dueDate,
        TaskStatus status,
        TaskPriority priority,
        Long teamId,
        Long assigneeId
) {}
