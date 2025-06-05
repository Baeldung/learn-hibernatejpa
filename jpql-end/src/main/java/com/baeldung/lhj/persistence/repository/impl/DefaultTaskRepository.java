package com.baeldung.lhj.persistence.repository.impl;

import java.time.LocalDate;
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
        EntityManager entityManager = getEntityManager();
        return entityManager
            .createQuery("SELECT t FROM Task t", Task.class)
            .getResultList();
    }

    @Override
    public List<Task> findByStatuses(List<TaskStatus> statuses) {
        EntityManager entityManager = getEntityManager();
        return entityManager
            .createQuery("SELECT t FROM Task t WHERE t.status IN (:statuses)", Task.class)
            .setParameter("statuses", statuses)
            .getResultList();
    }

    @Override
    public List<Task> findByWorkerEmail(String email) {
        EntityManager entityManager = getEntityManager();
        return entityManager
            .createQuery("SELECT t FROM Task t WHERE t.assignee.email =: email", Task.class)
            .setParameter("email", email)
            .getResultList();
    }

    @Override
    public int holdTasksByCampaignId(Long campaignId) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        int updatedCount = entityManager
            .createQuery("UPDATE Task t SET t.status = :onHoldStatus WHERE t.campaign.id = :campaignId")
            .setParameter("onHoldStatus", TaskStatus.ON_HOLD)
            .setParameter("campaignId", campaignId)
            .executeUpdate();
        entityManager.getTransaction().commit();
        return updatedCount;
    }

}