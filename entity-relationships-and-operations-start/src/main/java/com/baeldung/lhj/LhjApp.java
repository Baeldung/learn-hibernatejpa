package com.baeldung.lhj;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.lhj.persistence.model.Campaign;
import com.baeldung.lhj.persistence.model.Task;
import com.baeldung.lhj.persistence.util.JpaUtil;

public class LhjApp {

    public static void main(final String... args) {
        try {
            Logger logger = LoggerFactory.getLogger(LhjApp.class);
            logger.info("Running Learn Hibernate and JPA App");
        } finally {
            JpaUtil.closeEntityManagerFactory();
        }
    }

    // will be used throughout the lesson
    private static void createCampaign2WithTasks34() {
        // persist campaign, worker, and tasks
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Campaign campaign2 = new Campaign();
        campaign2.setName("Baeldung CS Article Marketing");
        campaign2.setCode("BAEL_CS_ARTICLE_MARKETING");

        Task task3 = new Task();
        task3.setName("Write a post");
        task3.setCampaign(campaign2);

        Task task4 = new Task();
        task4.setName("Share on LinkedIn");
        task4.setCampaign(campaign2);

        Set<Task> tasks = new HashSet<>();
        tasks.add(task3);
        tasks.add(task4);

        campaign2.setTasks(tasks);

        entityManager.persist(campaign2);

        transaction.commit();
        entityManager.close();
    }

}
