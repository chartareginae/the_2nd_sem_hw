package com.mipt.bayandinamariya.repository;

import com.mipt.bayandinamariya.model.Task;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Заглушка репозитория с фиксированными тестовыми данными.
 * Используется для демонстрации работы с несколькими реализациями
 * одного интерфейса и тестирования без постоянного хранилища.
 *
 */

public class StubTaskRepository implements TaskRepository {

    private static final List<Task> STUB_DATA = Arrays.asList(
            new Task("1", "Stub Task 1", "Fixed description 1", false),
            new Task("2", "Stub Task 2", "Fixed description 2", true)
    );

    @Override
    public Task save(Task task) {
        return task;
    }

    @Override
    public Optional<Task> findById(String id) {
        return STUB_DATA.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Task> findAll() {
        return STUB_DATA;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }
}