package com.airtribe.taskmaster.service;

import com.airtribe.taskmaster.dto.AttachmentResponse;
import com.airtribe.taskmaster.dto.CommentRequest;
import com.airtribe.taskmaster.dto.CommentResponse;
import com.airtribe.taskmaster.dto.TaskRequest;
import com.airtribe.taskmaster.dto.TaskResponse;
import com.airtribe.taskmaster.dto.TeamResponse;
import com.airtribe.taskmaster.dto.UserResponse;
import com.airtribe.taskmaster.entity.Attachment;
import com.airtribe.taskmaster.entity.Comment;
import com.airtribe.taskmaster.entity.Role;
import com.airtribe.taskmaster.entity.Task;
import com.airtribe.taskmaster.entity.TaskPriority;
import com.airtribe.taskmaster.entity.TaskStatus;
import com.airtribe.taskmaster.entity.Team;
import com.airtribe.taskmaster.entity.User;
import com.airtribe.taskmaster.exception.BadRequestException;
import com.airtribe.taskmaster.exception.ResourceNotFoundException;
import com.airtribe.taskmaster.repository.CommentRepository;
import com.airtribe.taskmaster.repository.TaskRepository;
import com.airtribe.taskmaster.repository.TeamRepository;
import com.airtribe.taskmaster.repository.UserRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public TaskResponse createTask(String email, TaskRequest request) {
        User reporter = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Team team = request.teamId() != null ? teamRepository.findById(request.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found")) : null;

        User assignee = request.assigneeId() != null ? userRepository.findById(request.assigneeId())
                .orElseThrow(() -> new ResourceNotFoundException("Assignee not found")) : null;

        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setDueDate(request.dueDate());
        task.setStatus(request.status() != null ? request.status() : TaskStatus.OPEN);
        task.setPriority(request.priority() != null ? request.priority() : TaskPriority.MEDIUM);
        task.setReporter(reporter);
        task.setAssignee(assignee);
        task.setTeam(team);

        Task savedTask = taskRepository.save(task);
        return toResponse(savedTask);
    }

    @Transactional(readOnly = true)
    public TaskResponse getTask(String email, Long taskId) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!canViewTask(currentUser, task)) {
            throw new BadRequestException("You are not allowed to view this task");
        }

        return toResponse(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> listTasks(String email, TaskStatus status, Long assigneeId, Long teamId, String search, String sortDirection) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, "dueDate");

        Specification<Task> spec = Specification.where((root, query, cb) -> {
            Join<Task, User> reporter = root.join("reporter", JoinType.LEFT);
            Join<Task, User> assignee = root.join("assignee", JoinType.LEFT);
            Join<Task, Team> team = root.join("team", JoinType.LEFT);
            Join<Team, User> teamMembers = team.join("members", JoinType.LEFT);

            Predicate reporterMatch = cb.equal(reporter.get("id"), currentUser.getId());
            Predicate assigneeMatch = cb.equal(assignee.get("id"), currentUser.getId());
            Predicate teamMembershipMatch = cb.equal(teamMembers.get("id"), currentUser.getId());
            return cb.or(reporterMatch, assigneeMatch, teamMembershipMatch);
        });

        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }

        if (assigneeId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("assignee").get("id"), assigneeId));
        }

        if (teamId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("team").get("id"), teamId));
        }

        if (search != null && !search.isBlank()) {
            String likeText = "%" + search.trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> {
                Predicate title = cb.like(cb.lower(root.get("title")), likeText);
                Predicate description = cb.like(cb.lower(root.get("description")), likeText);
                return cb.or(title, description);
            });
        }

        return taskRepository.findAll(spec, sort).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getMyTasks(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return taskRepository.findAll((root, query, cb) -> {
                    Join<Task, User> reporter = root.join("reporter", JoinType.LEFT);
                    Join<Task, User> assignee = root.join("assignee", JoinType.LEFT);
                    Predicate reporterMatch = cb.equal(reporter.get("id"), user.getId());
                    Predicate assigneeMatch = cb.equal(assignee.get("id"), user.getId());
                    return cb.or(reporterMatch, assigneeMatch);
                }, Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream().map(this::toResponse).toList();
    }

    @Transactional
    public TaskResponse updateTask(String email, Long taskId, TaskRequest request) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!canModifyTask(currentUser, task)) {
            throw new BadRequestException("You are not allowed to update this task");
        }

        if (request.title() != null) task.setTitle(request.title());
        if (request.description() != null) task.setDescription(request.description());
        if (request.dueDate() != null) task.setDueDate(request.dueDate());
        if (request.status() != null) task.setStatus(request.status());
        if (request.priority() != null) task.setPriority(request.priority());

        if (request.assigneeId() != null) {
            User assignee = userRepository.findById(request.assigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Assignee not found"));
            task.setAssignee(assignee);
        }

        if (request.teamId() != null) {
            Team team = teamRepository.findById(request.teamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
            task.setTeam(team);
        }

        return toResponse(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse completeTask(String email, Long taskId) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!canModifyTask(currentUser, task)) {
            throw new BadRequestException("You are not allowed to change this task");
        }

        task.setStatus(TaskStatus.DONE);
        return toResponse(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse assignTask(String email, Long taskId, Long assigneeId) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!canModifyTask(currentUser, task)) {
            throw new BadRequestException("You are not allowed to assign this task");
        }

        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignee not found"));
        task.setAssignee(assignee);
        task.setStatus(TaskStatus.IN_PROGRESS);
        return toResponse(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(String email, Long taskId) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!canModifyTask(currentUser, task)) {
            throw new BadRequestException("You are not allowed to delete this task");
        }

        taskRepository.delete(task);
    }

    @Transactional
    public CommentResponse addComment(String email, Long taskId, CommentRequest request) {
        User author = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!canViewTask(author, task)) {
            throw new BadRequestException("You are not allowed to comment on this task");
        }

        Comment comment = new Comment();
        comment.setTask(task);
        comment.setAuthor(author);
        comment.setContent(request.content());
        task.getComments().add(comment);
        taskRepository.save(task);
        return toCommentResponse(comment);
    }

    @Transactional
    public AttachmentResponse addAttachment(String email, Long taskId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Attachment file is required");
        }

        User uploader = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (!canViewTask(uploader, task)) {
            throw new BadRequestException("You are not allowed to attach files to this task");
        }

        Attachment attachment = new Attachment();
        attachment.setTask(task);
        attachment.setUploadedBy(uploader);
        attachment.setFileName(file.getOriginalFilename());
        attachment.setContentType(file.getContentType());
        attachment.setSize(file.getSize());
        try {
            attachment.setFileData(file.getBytes());
        } catch (Exception ex) {
            throw new BadRequestException("Unable to read attachment file");
        }

        task.getAttachments().add(attachment);
        taskRepository.save(task);
        return toAttachmentResponse(attachment);
    }

    private boolean canViewTask(User user, Task task) {
        if (task.getReporter() != null && task.getReporter().getId().equals(user.getId())) return true;
        if (task.getAssignee() != null && task.getAssignee().getId().equals(user.getId())) return true;
        if (task.getTeam() != null && task.getTeam().getMembers().stream().anyMatch(member -> member.getId().equals(user.getId()))) return true;
        return false;
    }

    private boolean canModifyTask(User user, Task task) {
        return canViewTask(user, task) || user.getRoles().contains(Role.ADMIN);
    }

    private TaskResponse toResponse(Task task) {
        List<CommentResponse> comments = task.getComments() == null ? List.of() :
                task.getComments().stream().sorted(Comparator.comparing(Comment::getCreatedAt)).map(this::toCommentResponse).toList();

        List<AttachmentResponse> attachments = task.getAttachments() == null ? List.of() :
                task.getAttachments().stream().sorted(Comparator.comparing(Attachment::getCreatedAt)).map(this::toAttachmentResponse).toList();

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getReporter() == null ? null : toUserResponse(task.getReporter()),
                task.getAssignee() == null ? null : toUserResponse(task.getAssignee()),
                task.getTeam() == null ? null : toTeamResponse(task.getTeam()),
                comments,
                attachments,
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    private CommentResponse toCommentResponse(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                toUserResponse(comment.getAuthor()),
                comment.getCreatedAt()
        );
    }

    private AttachmentResponse toAttachmentResponse(Attachment attachment) {
        return new AttachmentResponse(
                attachment.getId(),
                attachment.getFileName(),
                attachment.getContentType(),
                attachment.getSize(),
                attachment.getCreatedAt()
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

    private TeamResponse toTeamResponse(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getDescription(),
                toUserResponse(team.getOwner()),
                team.getMembers().stream().sorted(Comparator.comparing(User::getFullName)).map(this::toUserResponse).toList(),
                team.getCreatedAt(),
                team.getUpdatedAt()
        );
    }
}
