package com.mipt.bayandinamariya.controller;

import com.mipt.bayandinamariya.model.Task;
import com.mipt.bayandinamariya.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST контроллер для управления задачами через HTTP API.
 * Предоставляет полный CRUD API по эндпоинтам /api/tasks:
 *   GET /api/tasks — получить все задачи
 *   GET /api/tasks/{id} — получить задачу по ID
 *   POST /api/tasks — создать новую задачу
 *   PUT /api/tasks/{id} — обновить задачу
 *   DELETE /api/tasks/{id} — удалить задачу
*/

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        logger.info("Request: GET all tasks");
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable String id) {
        logger.info("Request: GET task by id={}", id);
        return taskService.getTask(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        logger.info("Request: POST new task: {}", task.getTitle());
        Task created = taskService.createTask(task);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable String id,
            @RequestBody Task updatedTask) {
        logger.info("Request: PUT update task id={}", id);
        return taskService.updateTask(id, updatedTask)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable String id) {
        logger.info("Request: DELETE task id={}", id);
        boolean deleted = taskService.deleteTask(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}