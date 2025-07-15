package com.baeldung.lhj;

import com.baeldung.lhj.persistence.model.Campaign;
import com.baeldung.lhj.persistence.repository.CampaignRepository;
import com.baeldung.lhj.persistence.repository.impl.DefaultCampaignRepository;
import jakarta.persistence.Cache;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LhjApp {

    public static void main(final String... args) {
        Logger logger = LoggerFactory.getLogger(LhjApp.class);
        logger.info("Running Learn Hibernate and JPA App");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("LHJ");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        CampaignRepository campaignRepository = new DefaultCampaignRepository();
        Campaign campaign = new Campaign("Test Campaign Code", "Test Campaign Name", "Test Campaign Description");
        campaignRepository.save(campaign);

        logger.info("Fetching Campaign - 1st Attempt");
        entityManager.find(Campaign.class, campaign.getId());

        logger.info("Fetching Campaign - 2nd Attempt");
        entityManager.find(Campaign.class, campaign.getId());

        logger.info("Fetching Campaign - 3rd Attempt");
        entityManager.find(Campaign.class, campaign.getId());

        logger.info("Clearing First-Level Cache");
        entityManager.clear();

        logger.info("Fetching Campaign - 4th Attempt");
        entityManager.find(Campaign.class, campaign.getId());

        Cache cache = entityManagerFactory.getCache();
        boolean isCached = cache.contains(Campaign.class, campaign.getId());
        if (isCached) {
            logger.info("Campaign with id {} is present in the second-level cache.", campaign.getId());
        }

        logger.info("Clearing Second-Level Cache");
        cache.evict(Campaign.class, campaign.getId());
        cache.evict(Campaign.class);
        cache.evictAll();

        isCached = cache.contains(Campaign.class, campaign.getId());
        if (!isCached) {
            logger.info("Campaign with id {} is not present in the second-level cache.", campaign.getId());
        }

        Statistics statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
        statistics.clear();

        logger.info("Fetching Campaign From First EntityManager");
        try (EntityManager entityManager1 = entityManagerFactory.createEntityManager()) {
            entityManager1.find(Campaign.class, campaign.getId());
        }
        logger.info("Cache Miss Count : {}", statistics.getSecondLevelCacheMissCount());
        logger.info("Cache Hit Count : {}", statistics.getSecondLevelCacheHitCount());

        logger.info("Fetching Campaign From Second EntityManager");
        try (EntityManager entityManager2 = entityManagerFactory.createEntityManager()) {
            entityManager2.find(Campaign.class, campaign.getId());
        }
        logger.info("Fetching Campaign From Third EntityManager");
        try (EntityManager entityManager3 = entityManagerFactory.createEntityManager()) {
            entityManager3.find(Campaign.class, campaign.getId());
        }
        logger.info("Cache Miss Count : {}", statistics.getSecondLevelCacheMissCount());
        logger.info("Cache Hit Count : {}", statistics.getSecondLevelCacheHitCount());
    }

}