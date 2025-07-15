package com.baeldung.lhj;

import java.time.LocalDate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.baeldung.lhj.persistence.model.Campaign;
import com.baeldung.lhj.persistence.model.Task;
import com.baeldung.lhj.persistence.model.TaskStatus;
import com.baeldung.lhj.persistence.model.Worker;
import com.baeldung.lhj.persistence.repository.CampaignRepository;
import com.baeldung.lhj.persistence.repository.TaskRepository;
import com.baeldung.lhj.persistence.repository.WorkerRepository;
import com.baeldung.lhj.persistence.repository.impl.DefaultCampaignRepository;
import com.baeldung.lhj.persistence.repository.impl.DefaultTaskRepository;
import com.baeldung.lhj.persistence.repository.impl.DefaultWorkerRepository;

public class EagerFetchUnitTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private Statistics stats;

    private CampaignRepository campaignRepository;
    private TaskRepository taskRepository;
    private WorkerRepository workerRepository;
    private Worker newWorker;

    @BeforeEach
    void setup() {
        emf = Persistence.createEntityManagerFactory("LHJ");
        em = emf.createEntityManager();
        stats = emf.unwrap(SessionFactory.class)
            .getStatistics();
        stats.clear();

        campaignRepository = new DefaultCampaignRepository();
        taskRepository = new DefaultTaskRepository();
        workerRepository = new DefaultWorkerRepository();

        em.getTransaction()
            .begin();

        newWorker = new Worker("johnsmith@baeldung.com", "John", "Smith");
        workerRepository.save(newWorker);

        createCampaignAndTasks(10);

        em.getTransaction()
            .commit();
    }

    @AfterEach
    void tearDown() {
        em.getTransaction()
            .begin();
        em.createQuery("delete from Task")
            .executeUpdate();
        em.createQuery("delete from Campaign")
            .executeUpdate();
        em.createQuery("delete from Worker")
            .executeUpdate();
        em.getTransaction()
            .commit();

        em.close();
        emf.close();
    }

    private void createCampaignAndTasks(int count) {
        for (int i = 1; i <= count; i++) {
            Campaign campaign = new Campaign("C" + i, "Campaign " + i, "Campaign " + i + " Description");
            campaignRepository.save(campaign);

            Task task = new Task("Task " + i, "Task " + i + " Description", LocalDate.now(), campaign, TaskStatus.TO_DO, newWorker);
            taskRepository.save(task);
        }
    }
}
