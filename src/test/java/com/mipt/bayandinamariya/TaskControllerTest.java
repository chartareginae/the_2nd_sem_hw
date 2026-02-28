package com.mipt.bayandinamariya;

import com.mipt.bayandinamariya.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Интеграционные тесты для TaskController.
 * Использует @SpringBootTest и TestRestTemplate для HTTP-запросов.
 * Содержит позитивные и негативные тест-кейсы для каждого CRUD-эндпоинта.
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
class TaskControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/api/tasks";
    }

    @Test
    void getAllTasks_positive() {
        ResponseEntity<Task[]> response = restTemplate.getForEntity(baseUrl, Task[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void createTask_positive() {
        Task newTask = new Task(null, "Test Task", "Test Description", false);

        ResponseEntity<Task> response = restTemplate.postForEntity(baseUrl, newTask, Task.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo("Test Task");
    }

    @Test
    void getTaskById_positive() {
        Task task = new Task(null, "Get Test", "Description", false);
        ResponseEntity<Task> created = restTemplate.postForEntity(baseUrl, task, Task.class);
        String id = Objects.requireNonNull(created.getBody()).getId();

        ResponseEntity<Task> response = restTemplate.getForEntity(baseUrl + "/" + id, Task.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo("Get Test");
    }

    @Test
    void updateTask_positive() {
        Task task = new Task(null, "Update Test", "Old Desc", false);
        ResponseEntity<Task> created = restTemplate.postForEntity(baseUrl, task, Task.class);
        String id = Objects.requireNonNull(created.getBody()).getId();

        Task updated = new Task(id, "Updated Title", "New Desc", true);

        ResponseEntity<Task> response = restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                Task.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getTitle()).isEqualTo("Updated Title");
        assertThat(response.getBody().isCompleted()).isTrue();
    }

    @Test
    void deleteTask_positive() {
        Task task = new Task(null, "Delete Test", "To Delete", false);
        ResponseEntity<Task> created = restTemplate.postForEntity(baseUrl, task, Task.class);
        String id = Objects.requireNonNull(created.getBody()).getId();

        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/" + id,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Task> getResponse = restTemplate.getForEntity(baseUrl + "/" + id, Task.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getTaskById_notFound() {
        String nonExistentId = "non-existent-id";

        ResponseEntity<Task> response = restTemplate.getForEntity(baseUrl + "/" + nonExistentId, Task.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateTask_notFound() {
        Task updated = new Task("non-existent", "Title", "Desc", false);

        ResponseEntity<Task> response = restTemplate.exchange(
                baseUrl + "/non-existent-id",
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                Task.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteTask_notFound() {
        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/non-existent-id",
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void createTask_withInvalidData() {
        Task emptyTask = new Task();

        ResponseEntity<Task> response = restTemplate.postForEntity(baseUrl, emptyTask, Task.class);

        assertThat(response.getStatusCode()).isIn(HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
    }
}