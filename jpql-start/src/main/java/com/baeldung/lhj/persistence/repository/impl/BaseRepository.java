package com.baeldung.lhj.persistence.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class BaseRepository {

    private static final EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("LHJ");

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}