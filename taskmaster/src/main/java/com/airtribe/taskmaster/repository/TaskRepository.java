package com.airtribe.taskmaster.repository;

import com.airtribe.taskmaster.entity.Task;
import com.airtribe.taskmaster.entity.TaskStatus;
import com.airtribe.taskmaster.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findByReporter(User reporter, Sort sort);
    List<Task> findByAssignee(User assignee, Sort sort);
    List<Task> findByStatus(TaskStatus status, Sort sort);
    List<Task> findAll(Specification<Task> spec, Sort sort);
}
