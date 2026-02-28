package com.mipt.bayandinamariya.service;

import com.mipt.bayandinamariya.model.Task;
import com.mipt.bayandinamariya.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Сервис статистики для демонстрации использования @Qualifier.
 * Инжектирует два репозитория одновременно: основной через @Primary
 * и заглушку через явное указание @Qualifier. Позволяет сравнивать
 * данные из разных источников.
 */

@Service
public class TaskStatisticsService {

    private static final Logger logger = LoggerFactory.getLogger(TaskStatisticsService.class);
    private final TaskRepository primaryRepository;
    private final TaskRepository stubRepository;

    public TaskStatisticsService(
            TaskRepository primaryRepository,
            @Qualifier("stubRepository") TaskRepository stubRepository) {
        this.primaryRepository = primaryRepository;
        this.stubRepository = stubRepository;
    }

    public String compareRepositories() {
        long primaryCount = primaryRepository.findAll().size();
        long stubCount = stubRepository.findAll().size();

        String result = String.format(
                "Primary repository: %d tasks | Stub repository: %d tasks",
                primaryCount, stubCount
        );

        logger.info("Repository comparison: {}", result);
        return result;
    }

    public List<Task> getTasksFromSource(String source) {
        if ("stub".equalsIgnoreCase(source)) {
            logger.info("Fetching tasks from StubRepository");
            return stubRepository.findAll();
        }
        logger.info("Fetching tasks from Primary repository");
        return primaryRepository.findAll();
    }
}