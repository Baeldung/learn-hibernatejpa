package com.baeldung.lhj.persistence.repository.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import com.baeldung.lhj.persistence.model.Campaign;
import com.baeldung.lhj.persistence.repository.CampaignRepository;

public class DefaultCampaignRepository extends BaseRepository implements CampaignRepository {

    @Override
    public Optional<Campaign> findById(Long id) {
        EntityManager entityManager = getEntityManager();
        return Optional.ofNullable(entityManager.find(Campaign.class, id));
    }

    @Override
    public Campaign save(Campaign campaign) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction()
            .begin();
        entityManager.persist(campaign);
        entityManager.getTransaction()
            .commit();
        return campaign;
    }

    @Override
    public List<Campaign> findAll() {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Campaign> cq = cb.createQuery(Campaign.class);
        Root<Campaign> rootEntry = cq.from(Campaign.class);
        cq.select(rootEntry);
        TypedQuery<Campaign> allQuery = entityManager.createQuery(cq);
        return allQuery.getResultList();
    }

}
