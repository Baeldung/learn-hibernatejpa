package com.baeldung.lhj;

import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LhjApp {

    public static void main(final String... args) {
        Logger logger = LoggerFactory.getLogger(LhjApp.class);
        logger.info("Running Learn Hibernate and JPA App");

        Persistence.createEntityManagerFactory("LHJ");
    }
}
