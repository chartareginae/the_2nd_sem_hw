package com.mipt.bayandinamariya.repository;

import com.mipt.bayandinamariya.model.Task;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс репозитория для CRUD-операций с задачами.
 * Определяет контракт для хранения и извлечения задач.
 * Реализуется классами InMemoryTaskRepository и StubTaskRepository.
 */

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(String id);
    List<Task> findAll();
    boolean deleteById(String id);
}