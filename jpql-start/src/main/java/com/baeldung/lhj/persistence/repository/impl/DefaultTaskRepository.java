package com.baeldung.lhj.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import com.baeldung.lhj.persistence.model.Task;
import com.baeldung.lhj.persistence.model.TaskStatus;
import com.baeldung.lhj.persistence.repository.TaskRepository;
import jakarta.persistence.EntityManager;

public class DefaultTaskRepository extends BaseRepository implements TaskRepository {

    public DefaultTaskRepository() {
        super();
    }

    @Override
    public Optional<Task> findById(Long id) {
        EntityManager entityManager = getEntityManager();
        Task retrievedTask = entityManager.find(Task.class, id);
        return Optional.ofNullable(retrievedTask);
    }

    @Override
    public Task save(Task task) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();
        return task;
    }

    @Override
    public List<Task> findAll() {
        return List.of();
    }

    @Override
    public List<Task> findByStatuses(List<TaskStatus> statuses) {
        return List.of();
    }

    @Override
    public List<Task> findByWorkerEmail(String email) {
        return List.of();
    }

    @Override
    public int holdTasksByCampaignId(Long campaignId) {
        return 0;
    }

}