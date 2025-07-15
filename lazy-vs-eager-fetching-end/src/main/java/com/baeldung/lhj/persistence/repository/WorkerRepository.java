package com.baeldung.lhj.persistence.repository;

import com.baeldung.lhj.persistence.model.Worker;

public interface WorkerRepository {
    Worker findById(Long id);

    Worker save(Worker worker);
}
