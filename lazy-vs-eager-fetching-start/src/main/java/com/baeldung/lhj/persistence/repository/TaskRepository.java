package com.baeldung.lhj.persistence.repository;

import com.baeldung.lhj.persistence.model.Task;

import java.util.List;

public interface TaskRepository {
    Task findById(Long id);

    Task save(Task task);

    List<Task> findAll();

    List<Task> findByNameContainingAndAssigneeId(String name, Long assigneeId);
}
