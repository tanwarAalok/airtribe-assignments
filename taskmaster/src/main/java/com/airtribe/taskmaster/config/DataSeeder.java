package com.airtribe.taskmaster.config;

import com.airtribe.taskmaster.entity.Role;
import com.airtribe.taskmaster.entity.Task;
import com.airtribe.taskmaster.entity.TaskPriority;
import com.airtribe.taskmaster.entity.TaskStatus;
import com.airtribe.taskmaster.entity.Team;
import com.airtribe.taskmaster.entity.User;
import com.airtribe.taskmaster.repository.TaskRepository;
import com.airtribe.taskmaster.repository.TeamRepository;
import com.airtribe.taskmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        User admin = new User();
        admin.setFullName("System Admin");
        admin.setEmail("admin@taskmaster.io");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRoles(Set.of(Role.ADMIN));
        userRepository.save(admin);

        User member = new User();
        member.setFullName("Jane Developer");
        member.setEmail("jane@taskmaster.io");
        member.setPassword(passwordEncoder.encode("jane123"));
        member.setRoles(Set.of(Role.MEMBER));
        userRepository.save(member);

        Team alpha = new Team();
        alpha.setName("Alpha Team");
        alpha.setDescription("Product delivery squad");
        alpha.setOwner(admin);
        alpha.getMembers().add(admin);
        alpha.getMembers().add(member);
        teamRepository.save(alpha);

        Task task = new Task();
        task.setTitle("Set up sprint board");
        task.setDescription("Create the initial Jira-like board for the sprint demo");
        task.setStatus(TaskStatus.OPEN);
        task.setPriority(TaskPriority.HIGH);
        task.setDueDate(LocalDate.now().plusDays(3));
        task.setReporter(admin);
        task.setAssignee(member);
        task.setTeam(alpha);
        taskRepository.save(task);
    }
}
