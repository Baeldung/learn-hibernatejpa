package com.baeldung.lhj.persistence.repository.impl;

import com.baeldung.lhj.persistence.model.TaskStatus;
import com.baeldung.lhj.persistence.model.Worker;
import com.baeldung.lhj.persistence.repository.WorkerRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class DefaultWorkerRepository extends BaseRepository implements WorkerRepository {

    public DefaultWorkerRepository() {
        super();
    }

    @Override
    public Optional<Worker> findById(Long id) {
        EntityManager entityManager = getEntityManager();
        Worker retrievedWorker = entityManager.find(Worker.class, id);
        return Optional.ofNullable(retrievedWorker);
    }

    @Override
    public Worker save(Worker worker) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(worker);
        entityManager.getTransaction().commit();
        return worker;
    }

    @Override
    public List<Worker> findWorkersWithActiveTasks() {
        EntityManager entityManager = getEntityManager();
        return entityManager
            .createQuery("SELECT DISTINCT w FROM Worker w JOIN w.tasks t WHERE t.status = :inProgressStatus", Worker.class)
            .setParameter("inProgressStatus", TaskStatus.IN_PROGRESS)
            .getResultList();
    }

    @Override
    public List<Worker> findAllOrderByFirstName() {
        EntityManager entityManager = getEntityManager();
        return entityManager
            .createQuery("SELECT w FROM Worker w ORDER BY w.firstName ASC", Worker.class)
            .getResultList();
    }
    
}