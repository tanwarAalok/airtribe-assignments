package com.airtribe.taskmaster.controller;

import com.airtribe.taskmaster.dto.AuthResponse;
import com.airtribe.taskmaster.dto.LoginRequest;
import com.airtribe.taskmaster.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("service", "taskmaster");
        response.put("status", "ready");
        response.put("timestamp", Instant.now());
        response.put("availableEndpoints", new String[] {
                "GET /api/test/health",
                "GET /api/test/demo-users",
                "POST /api/test/login-demo",
                "POST /api/auth/register",
                "POST /api/auth/login",
                "GET /api/users/me",
                "GET /api/teams",
                "GET /api/tasks",
                "POST /api/tasks",
                "POST /api/tasks/{taskId}/comments"
        });
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "UP");
        response.put("service", "taskmaster");
        response.put("timestamp", Instant.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo-users")
    public ResponseEntity<Map<String, Object>> demoUsers() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("admin", Map.of(
                "email", "admin@taskmaster.io",
                "password", "admin123",
                "role", "ADMIN"
        ));
        response.put("member", Map.of(
                "email", "jane@taskmaster.io",
                "password", "jane123",
                "role", "MEMBER"
        ));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login-demo")
    public ResponseEntity<AuthResponse> loginDemo(@RequestBody(required = false) LoginRequest request) {
        LoginRequest effectiveRequest = request != null
                ? request
                : new LoginRequest("admin@taskmaster.io", "admin123");
        return ResponseEntity.ok(authService.login(effectiveRequest));
    }
}
