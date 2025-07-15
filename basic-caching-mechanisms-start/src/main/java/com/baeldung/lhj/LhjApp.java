package com.baeldung.lhj;

import com.baeldung.lhj.persistence.model.Campaign;
import com.baeldung.lhj.persistence.repository.CampaignRepository;
import com.baeldung.lhj.persistence.repository.impl.DefaultCampaignRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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
    }

}