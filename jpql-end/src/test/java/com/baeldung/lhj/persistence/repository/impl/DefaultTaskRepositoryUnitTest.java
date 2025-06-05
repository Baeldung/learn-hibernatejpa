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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultTaskRepositoryUnitTest {
    CampaignRepository campaignRepository = new DefaultCampaignRepository();
    WorkerRepository workerRepository = new DefaultWorkerRepository();
    TaskRepository taskRepository = new DefaultTaskRepository();

    @Test
    public void givenExistingTask_whenFindById_thenTaskRetrieved() {
        // given
        Campaign campaign = new Campaign("CTASK-1", "Campaign 1", "Campaign 1 Description");
        campaignRepository.save(campaign);

        Task existingTask = new Task("Task 1", "Task 1 Description", LocalDate.now(), campaign, TaskStatus.TO_DO, null);
        taskRepository.save(existingTask);

        // when
        Task retrievedTask = taskRepository.findById(existingTask.getId()).get();

        // then
        Assertions.assertEquals(existingTask, retrievedTask);
    }

    @Test
    public void givenExistingTask_whenFindByNonExistingId_thenNoTaskRetrieved() {
        // given
        Campaign campaign = new Campaign("CTASK-2", "Campaign 2", "Campaign 2 Description");
        campaignRepository.save(campaign);

        Task existingTask = new Task("Task 2", "Task 2 Description", LocalDate.now(), campaign, TaskStatus.TO_DO, null);
        taskRepository.save(existingTask);

        // when
        Optional<Task> retrievedTask = taskRepository.findById(99L);

        // then
        Assertions.assertTrue(retrievedTask.isEmpty());
    }

    @Test
    public void givenOnHoldTasks_whenFindByStatuses_thenAllOnHoldTasksRetrieved() {
        // given
        Campaign campaign = new Campaign("CTASK-4", "Campaign 4", "Campaign 4 Description");
        campaignRepository.save(campaign);

        Task onHoldTask1 = new Task("Task 3", "Task 3 Description", LocalDate.now(), campaign, TaskStatus.ON_HOLD, null);
        Task onHoldTask2 = new Task("Task 4", "Task 4 Description", LocalDate.now(), campaign, TaskStatus.ON_HOLD, null);
        Task inProgressTask = new Task("Task 5", "Task 5 Description", LocalDate.now(), campaign, TaskStatus.IN_PROGRESS, null);
    
        taskRepository.save(onHoldTask1);
        taskRepository.save(onHoldTask2);
        taskRepository.save(inProgressTask);

        // when
        List<Task> retrievedTasks = taskRepository.findByStatuses(List.of(TaskStatus.ON_HOLD));

        // then
        Assertions.assertTrue(retrievedTasks.contains(onHoldTask1));
        Assertions.assertTrue(retrievedTasks.contains(onHoldTask2));
        Assertions.assertFalse(retrievedTasks.contains(inProgressTask));
    }

    @Test
    public void givenTasksAssignedToWorker_whenFindByWorkerEmail_thenCorrectTasksRetrieved() {
        // given
        Campaign campaign = new Campaign("CTASK-8", "Campaign 8", "Campaign 8 Description");
        campaignRepository.save(campaign);

        String email = "alex.smith@baeldung.com";
        Worker worker = new Worker(email, "Alex", "Smith");
        workerRepository.save(worker);

        Task task1 = new Task("Task 18", "Task 18 Description", LocalDate.now(), campaign, TaskStatus.TO_DO, worker);
        Task task2 = new Task("Task 19", "Task 19 Description", LocalDate.now(), campaign, TaskStatus.IN_PROGRESS, worker);
        taskRepository.save(task1);
        taskRepository.save(task2);

        // when
        List<Task> retrievedTasks = taskRepository.findByWorkerEmail(email);

        // then
        Assertions.assertEquals(2, retrievedTasks.size());
        Assertions.assertTrue(retrievedTasks.contains(task1));
        Assertions.assertTrue(retrievedTasks.contains(task2));
    }

    @Test
    public void givenTasksInCampaign_whenHoldTasksByCompaignId_thenAllTasksUpdated() {
        // given
        Campaign campaign = new Campaign("CTASK-10", "Campaign 10", "Campaign 10 Description");
        campaignRepository.save(campaign);

        Task task1 = new Task("Task 24", "Task 24 Description", LocalDate.now(), campaign, TaskStatus.TO_DO, null);
        Task task2 = new Task("Task 25", "Task 25 Description", LocalDate.now(), campaign, TaskStatus.IN_PROGRESS, null);
        taskRepository.save(task1);
        taskRepository.save(task2);

        // when
        int updatedCount = taskRepository.holdTasksByCampaignId(campaign.getId());

        // then
        Assertions.assertEquals(2, updatedCount);
        Task retrievedTask1 = taskRepository.findById(task1.getId()).get();
        Task retrievedTask2 = taskRepository.findById(task2.getId()).get();
        Assertions.assertEquals(TaskStatus.ON_HOLD, retrievedTask1.getStatus());
        Assertions.assertEquals(TaskStatus.ON_HOLD, retrievedTask2.getStatus());
    }

}