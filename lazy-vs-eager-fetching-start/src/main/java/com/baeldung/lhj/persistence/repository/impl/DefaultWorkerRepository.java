package com.baeldung.lhj.persistence.repository.impl;

import com.baeldung.lhj.persistence.model.Worker;
import com.baeldung.lhj.persistence.repository.WorkerRepository;
import com.baeldung.lhj.persistence.util.JpaUtil;

import jakarta.persistence.EntityManager;

public class DefaultWorkerRepository implements WorkerRepository {

    @Override
    public Worker findById(Long id) {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            return entityManager.find(Worker.class, id);
        }
    }

    @Override
    public Worker save(Worker worker) {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(worker);
            entityManager.getTransaction().commit();
            return worker;
        }
    }
}
