package com.baeldung.lhj;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class LhjApp {

public static Logger logger = LoggerFactory.getLogger(LhjApp.class);
	
    public static CampaignRepository campaignRepository = new DefaultCampaignRepository();
    public static TaskRepository taskRepository = new DefaultTaskRepository();
    public static WorkerRepository workerRepository = new DefaultWorkerRepository();
    
    public static void createTestData() {
	   // create Campaign
       Campaign newCampaign = new Campaign("C1", "Campaign 1", "Campaign 1 Description");
       campaignRepository.save(newCampaign);
       logger.info("Saved new Campaign: {}", newCampaign);

       // create Worker
       Worker newWorker = new Worker("john@test.com", "John", "Doe");
       workerRepository.save(newWorker);
       logger.info("Saved new Worker: {}", newWorker);

       // create Tasks
       Task newTask = new Task("Task 1", "Task 1 Description", LocalDate.now(), newCampaign, TaskStatus.TO_DO, newWorker);
       taskRepository.save(newTask);
       logger.info("Saved new Task: {}", newTask);
       Task onHoldTask = new Task("Task 2", "Task 2 Description", LocalDate.now(), newCampaign, TaskStatus.ON_HOLD, null);
       taskRepository.save(onHoldTask);
       logger.info("Saved new Task: {}", onHoldTask);
       
       Worker activeWorker = new Worker("active.worker@baeldung.com", "Active", "Worker");
       Worker idleWorker = new Worker("idle.worker@worker.com", "Idle", "Worker");
       workerRepository.save(activeWorker);
       workerRepository.save(idleWorker);
       
       Task activeTask = new Task("Active Task", "Active Task Description", LocalDate.now(), newCampaign, TaskStatus.IN_PROGRESS, activeWorker);
       Task idleTask = new Task("Idle Task", "Idle Task Description", LocalDate.now(), newCampaign, TaskStatus.TO_DO, idleWorker);
       taskRepository.save(activeTask);
       taskRepository.save(idleTask);

    }
	
    public static void main(final String... args) {
        logger.info("Running Learn Hibernate and JPA App");
        
        createTestData();
    }

}