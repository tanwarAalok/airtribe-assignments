package com.airtribe.taskmaster.service;

import com.airtribe.taskmaster.dto.TeamRequest;
import com.airtribe.taskmaster.dto.TeamResponse;
import com.airtribe.taskmaster.dto.UserResponse;
import com.airtribe.taskmaster.entity.Team;
import com.airtribe.taskmaster.entity.User;
import com.airtribe.taskmaster.exception.BadRequestException;
import com.airtribe.taskmaster.exception.ResourceNotFoundException;
import com.airtribe.taskmaster.repository.TeamRepository;
import com.airtribe.taskmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Transactional
    public TeamResponse createTeam(String email, TeamRequest request) {
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Team team = new Team();
        team.setName(request.name());
        team.setDescription(request.description());
        team.setOwner(owner);
        team.getMembers().add(owner);
        Team saved = teamRepository.save(team);
        return toResponse(saved);
    }

    public List<TeamResponse> listTeamsForUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return teamRepository.findByMembersContaining(user).stream()
                .sorted(Comparator.comparing(Team::getName))
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public TeamResponse addMemberToTeam(String email, Long teamId, Long userId) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User member = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!team.getOwner().getId().equals(currentUser.getId()) && !currentUser.getRoles().contains(com.airtribe.taskmaster.entity.Role.ADMIN)) {
            throw new BadRequestException("Only the team owner can invite members");
        }

        team.getMembers().add(member);
        member.getTeams().add(team);
        teamRepository.save(team);
        return toResponse(team);
    }

    private TeamResponse toResponse(Team team) {
        List<UserResponse> members = team.getMembers().stream()
                .sorted(Comparator.comparing(User::getFullName))
                .map(this::toUserResponse)
                .toList();

        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                toUserResponse(team.getOwner()),
                members,
                team.getCreatedAt(),
                team.getUpdatedAt()
        );
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRoles(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
