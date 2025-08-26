package com.baeldung.lhj.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import com.baeldung.lhj.persistence.model.Campaign;
import com.baeldung.lhj.persistence.repository.CampaignRepository;
import com.baeldung.lhj.persistence.util.JpaUtil;

import jakarta.persistence.EntityManager;

public class DefaultCampaignRepository implements CampaignRepository {

    public DefaultCampaignRepository() {
        super();
    }

    @Override
    public Optional<Campaign> findById(Long id) {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            Campaign retrievedCampaign = entityManager.find(Campaign.class, id);
            return Optional.ofNullable(retrievedCampaign);
        }
    }

    @Override
    public Campaign save(Campaign campaign) {
        try (EntityManager entityManager = JpaUtil.getEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(campaign);
            entityManager.getTransaction().commit();
            return campaign;
        }
    }

    @Override
    public List<Campaign> findAll() {
        return List.of();
    }

    @Override
    public List<Campaign> findByNameOrDescriptionContaining(String text) {
        return List.of();
    }

    @Override
    public int deleteCampaignsWithoutTasks() {
        return 0;
    }

}