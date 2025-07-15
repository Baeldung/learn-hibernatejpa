package com.baeldung.lhj.persistence.repository.impl;

import java.util.Optional;

import jakarta.persistence.EntityManager;

import com.baeldung.lhj.persistence.model.Worker;
import com.baeldung.lhj.persistence.repository.WorkerRepository;

public class DefaultWorkerRepository extends BaseRepository implements WorkerRepository {

    @Override
    public Optional<Worker> findById(Long id) {
        EntityManager entityManager = getEntityManager();
        return Optional.ofNullable(entityManager.find(Worker.class, id));
    }

    @Override
    public Worker save(Worker worker) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction()
            .begin();
        entityManager.persist(worker);
        entityManager.getTransaction()
            .commit();
        return worker;
    }
}
