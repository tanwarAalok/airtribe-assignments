package com.airtribe.taskmaster.repository;

import com.airtribe.taskmaster.entity.Team;
import com.airtribe.taskmaster.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByMembersContaining(User user);
}
