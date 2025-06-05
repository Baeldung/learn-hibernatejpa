package com.baeldung.lhj.persistence.repository.impl;

import com.baeldung.lhj.persistence.model.Campaign;
import com.baeldung.lhj.persistence.model.Task;
import com.baeldung.lhj.persistence.model.TaskStatus;
import com.baeldung.lhj.persistence.model.Worker;
import com.baeldung.lhj.persistence.repository.CampaignRepository;
import com.baeldung.lhj.persistence.repository.TaskRepository;
import com.baeldung.lhj.persistence.repository.WorkerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class DefaultWorkerRepositoryUnitTest {
    CampaignRepository campaignRepository =  new DefaultCampaignRepository();
    TaskRepository taskRepository = new DefaultTaskRepository();
    WorkerRepository workerRepository = new DefaultWorkerRepository();

    @Test
    public void givenExistingWorker_whenFindById_thenWorkerRetrieved() {
        // given
        Worker existingWorker = new Worker("johnTest1@test.com", "John", "Doe");
        workerRepository.save(existingWorker);

        // when
        Worker retrievedWorker = workerRepository.findById(existingWorker.getId()).get();

        // then
        Assertions.assertEquals(existingWorker, retrievedWorker);
    }

    @Test
    public void givenExistingWorker_whenFindByNonExistingId_thenNoWorkerRetrieved() {
        // given
        Worker existingWorker = new Worker("johnTest2@test.com", "John", "Doe");
        workerRepository.save(existingWorker);

        // when
        Optional<Worker> retrievedWorker = workerRepository.findById(99L);

        // then
        Assertions.assertTrue(retrievedWorker.isEmpty());
    }

    @Test
    public void givenWorkersWithActiveTasks_whenFindWorkersWithActiveTasks_thenCorrectWorkersRetrieved() {
        // given
        Campaign campaign = new Campaign("WC-1", "Worker Campaign 1", "Worker Campaign 1 Description");
        campaignRepository.save(campaign);

        Worker activeWorker = new Worker("active.worker.test@baeldung.com", "Active", "Worker");
        Worker idleWorker = new Worker("idle.worker.test@worker.com", "Idle", "Worker");
        workerRepository.save(activeWorker);
        workerRepository.save(idleWorker);

        Task activeTask = new Task("Active Task", "Active Task Description", LocalDate.now(), campaign, TaskStatus.IN_PROGRESS, activeWorker);
        Task idleTask = new Task("Idle Task", "Idle Task Description", LocalDate.now(), campaign, TaskStatus.TO_DO, idleWorker);
        taskRepository.save(activeTask);
        taskRepository.save(idleTask);

        // when
        List<Worker> workersWithActiveTasks = workerRepository.findWorkersWithActiveTasks();

        // then
        Assertions.assertTrue(workersWithActiveTasks.contains(activeWorker));
        Assertions.assertFalse(workersWithActiveTasks.contains(idleWorker));
    }

    @Test
    public void givenWorkers_whenFindAllOrderByFirstName_thenWorkersAreSortedByFirstNameAsc() {
        // given
        Worker alice = new Worker("alice.smith@baeldung.com", "Alice", "Smith");
        Worker bob = new Worker("bob.jones@baeldung.com", "Bob", "Jones");
        Worker charlie = new Worker("charlie.brown@baeldung.com", "Charlie", "Brown");
        workerRepository.save(alice);
        workerRepository.save(bob);
        workerRepository.save(charlie);

        // when
        List<Worker> retrievedWorkers = workerRepository.findAllOrderByFirstName();

        // then
        Assertions.assertTrue(retrievedWorkers.indexOf(alice) < retrievedWorkers.indexOf(bob));
        Assertions.assertTrue(retrievedWorkers.indexOf(bob) < retrievedWorkers.indexOf(charlie));
    }

}