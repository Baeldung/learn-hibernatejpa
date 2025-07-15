package com.baeldung.lhj.persistence.repository.impl;

import com.baeldung.lhj.persistence.model.Worker;
import com.baeldung.lhj.persistence.repository.WorkerRepository;
import jakarta.persistence.EntityManager;

public class DefaultWorkerRepository extends BaseRepository implements WorkerRepository {

    @Override
    public Worker findById(Long id) {
        EntityManager entityManager = getEntityManager();
        return entityManager.find(Worker.class, id);
    }

    @Override
    public Worker save(Worker worker) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(worker);
        entityManager.getTransaction().commit();
        return worker;
    }
}
