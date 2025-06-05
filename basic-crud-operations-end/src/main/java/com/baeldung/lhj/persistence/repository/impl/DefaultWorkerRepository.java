package com.baeldung.lhj.persistence.repository.impl;

import com.baeldung.lhj.persistence.model.Worker;
import com.baeldung.lhj.persistence.repository.WorkerRepository;
import jakarta.persistence.EntityManager;

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
    public void update(Long id, Worker worker) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        Worker retrievedWorker = entityManager.find(Worker.class, id);
        retrievedWorker.setFirstName(worker.getFirstName());
        retrievedWorker.setLastName(worker.getLastName());

        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();

        Worker retrievedWorker = entityManager.find(Worker.class, id);
        entityManager.remove(retrievedWorker);

        entityManager.getTransaction().commit();
    }

}