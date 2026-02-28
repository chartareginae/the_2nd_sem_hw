package com.mipt.bayandinamariya.service;

import com.mipt.bayandinamariya.model.Task;
import com.mipt.bayandinamariya.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Основной сервис для бизнес-логики управления задачами.
 * Реализует CRUD-операции через репозиторий.
 * Демонстрирует использование аннотаций жизненного цикла:
 * @PostConstruct для инициализации кэша и @PreDestroy для очистки ресурсов.
 */

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    private final Map<String, Task> taskCache = new ConcurrentHashMap<>();

    @Value("${app.name:TaskManager}")
    private String appName;

    @Value("${app.version:1.0.0}")
    private String appVersion;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        logger.info("TaskService initialized with repository: {}",
                taskRepository.getClass().getSimpleName());
    }

    @PostConstruct
    public void initCache() {
        logger.info("[@PostConstruct] Initializing cache for {}", appName);
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            taskCache.put(task.getId(), task);
            logger.debug("Cached task: {}", task.getTitle());
        }
        logger.info("[@PostConstruct] Cache initialized with {} tasks", taskCache.size());
    }

    @PreDestroy
    public void cleanup() {
        logger.info("[@PreDestroy] Cleaning up resources for {}", appName);
        logger.info("[@PreDestroy] Tasks in cache: {}", taskCache.size());
        taskCache.clear();
        logger.info("[@PreDestroy] Cleanup completed");
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> getTask(String id) {
        return taskRepository.findById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> updateTask(String id, Task updatedTask) {
        return taskRepository.findById(id).map(existing -> {
            existing.setTitle(updatedTask.getTitle());
            existing.setDescription(updatedTask.getDescription());
            existing.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(existing);
        });
    }

    public boolean deleteTask(String id) {
        return taskRepository.deleteById(id);
    }

    public String getAppName() {
        return appName;
    }

    public String getAppVersion() {
        return appVersion;
    }
}