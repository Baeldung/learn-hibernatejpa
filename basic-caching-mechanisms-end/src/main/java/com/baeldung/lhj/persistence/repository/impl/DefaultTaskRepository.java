package com.baeldung.lhj.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.baeldung.lhj.persistence.model.Task;
import com.baeldung.lhj.persistence.repository.TaskRepository;

public class DefaultTaskRepository extends BaseRepository implements TaskRepository {

    @Override
    public Optional<Task> findById(Long id) {
        EntityManager entityManager = getEntityManager();
        return Optional.ofNullable(entityManager.find(Task.class, id));
    }

    @Override
    public Task save(Task task) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction()
            .begin();
        entityManager.persist(task);
        entityManager.getTransaction()
            .commit();
        return task;
    }

    @Override
    public List<Task> findAll() {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> rootEntry = cq.from(Task.class);
        cq.select(rootEntry);
        TypedQuery<Task> allQuery = entityManager.createQuery(cq);
        return allQuery.getResultList();
    }

    @Override
    public List<Task> findByNameContainingAndAssigneeId(String name, Long assigneeId) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("FROM Task t WHERE t.name LIKE :name AND t.assignee.id = :assigneeId");
        query.setParameter("name", "%" + name + "%");
        query.setParameter("assigneeId", assigneeId);
        return query.getResultList();
    }
}
