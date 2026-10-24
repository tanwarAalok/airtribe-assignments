package com.airtribe.taskmaster.controller;

import com.airtribe.taskmaster.dto.AttachmentResponse;
import com.airtribe.taskmaster.dto.CommentRequest;
import com.airtribe.taskmaster.dto.CommentResponse;
import com.airtribe.taskmaster.dto.TaskRequest;
import com.airtribe.taskmaster.dto.TaskResponse;
import com.airtribe.taskmaster.entity.TaskStatus;
import com.airtribe.taskmaster.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> listTasks(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "asc") String sort) {
        return ResponseEntity.ok(taskService.listTasks(userDetails.getUsername(), status, assigneeId, teamId, search, sort));
    }

    @GetMapping("/my")
    public ResponseEntity<List<TaskResponse>> getMyTasks(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.getMyTasks(userDetails.getUsername()));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTask(userDetails.getUsername(), taskId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId,
            @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(userDetails.getUsername(), taskId, request));
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<TaskResponse> completeTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.completeTask(userDetails.getUsername(), taskId));
    }

    @PatchMapping("/{taskId}/assign")
    public ResponseEntity<TaskResponse> assignTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId,
            @RequestParam Long assigneeId) {
        return ResponseEntity.ok(taskService.assignTask(userDetails.getUsername(), taskId, assigneeId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId) {
        taskService.deleteTask(userDetails.getUsername(), taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{taskId}/comments")
    public ResponseEntity<CommentResponse> addComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId,
            @Valid @RequestBody CommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addComment(userDetails.getUsername(), taskId, request));
    }

    @PostMapping(value = "/{taskId}/attachments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentResponse> addAttachment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long taskId,
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addAttachment(userDetails.getUsername(), taskId, file));
    }
}
