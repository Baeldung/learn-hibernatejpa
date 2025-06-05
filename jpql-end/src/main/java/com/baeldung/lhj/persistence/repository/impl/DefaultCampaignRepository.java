package com.baeldung.lhj.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import com.baeldung.lhj.persistence.model.Campaign;
import com.baeldung.lhj.persistence.repository.CampaignRepository;
import jakarta.persistence.EntityManager;

public class DefaultCampaignRepository extends BaseRepository implements CampaignRepository {

    public DefaultCampaignRepository() {
        super();
    }

    @Override
    public Optional<Campaign> findById(Long id) {
        EntityManager entityManager = getEntityManager();
        Campaign retrievedCampaign = entityManager.find(Campaign.class, id);
        return Optional.ofNullable(retrievedCampaign);
    }

    @Override
    public Campaign save(Campaign campaign) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(campaign);
        entityManager.getTransaction().commit();
        return campaign;
    }

    @Override
    public List<Campaign> findAll() {
        EntityManager entityManager = getEntityManager();
        return entityManager
            .createQuery("SELECT c FROM Campaign c", Campaign.class)
            .getResultList();
    }

    @Override
    public Optional<Campaign> findByCodeAndName(String code, String name) {
        EntityManager entityManager = getEntityManager();
        Campaign result = entityManager
            .createQuery("SELECT c FROM Campaign c WHERE c.code = ?1 AND c.name = ?2", Campaign.class)
            .setParameter(1, code)
            .setParameter(2, name)
            .getSingleResult();
        return Optional.ofNullable(result);
    }


}