package com.baeldung.lhj.persistence.repository.impl;

import java.util.Optional;

import com.baeldung.lhj.persistence.model.Task;
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
    public void update(Long id, Task task) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        Task retrievedTask = entityManager.find(Task.class, id);
        retrievedTask.setName(task.getName());
        retrievedTask.setDescription(task.getDescription());
        retrievedTask.setDueDate(task.getDueDate());
        retrievedTask.setStatus(task.getStatus());

        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        Task retrievedTask = entityManager.find(Task.class, id);
        entityManager.remove(retrievedTask);

        entityManager.getTransaction().commit();
    }

}