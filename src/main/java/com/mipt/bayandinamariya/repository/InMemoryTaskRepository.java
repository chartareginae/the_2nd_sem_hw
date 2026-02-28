package com.mipt.bayandinamariya.repository;

import com.mipt.bayandinamariya.model.Task;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Основной репозиторий для хранения задач в оперативной памяти.
 * Использует ConcurrentHashMap для потокобезопасного хранения.
 * Помечен аннотацией @Primary как репозиторий по умолчанию.
*/

@Primary
@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<String, Task> taskStore = new ConcurrentHashMap<>();

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(java.util.UUID.randomUUID().toString());
        }
        taskStore.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(taskStore.get(id));
    }

    @Override
    public List<Task> findAll() {
        return taskStore.values().stream().collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(String id) {
        return taskStore.remove(id) != null;
    }
}