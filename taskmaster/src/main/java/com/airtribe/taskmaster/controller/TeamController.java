package com.airtribe.taskmaster.controller;

import com.airtribe.taskmaster.dto.TeamRequest;
import com.airtribe.taskmaster.dto.TeamResponse;
import com.airtribe.taskmaster.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody TeamRequest request) {
        return ResponseEntity.ok(teamService.createTeam(userDetails.getUsername(), request));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponse>> listTeams(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(teamService.listTeamsForUser(userDetails.getUsername()));
    }

    @PostMapping("/{teamId}/members/{userId}")
    public ResponseEntity<TeamResponse> addMemberToTeam(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long teamId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(teamService.addMemberToTeam(userDetails.getUsername(), teamId, userId));
    }
}
