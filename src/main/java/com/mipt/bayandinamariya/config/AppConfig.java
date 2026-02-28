package com.mipt.bayandinamariya.config;

import com.mipt.bayandinamariya.repository.StubTaskRepository;
import com.mipt.bayandinamariya.repository.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для объявления бинов Spring.
 * Содержит @Bean-методы для создания StubTaskRepository
 * и других компонентов, которые не аннотированы @Component.
 */

@Configuration
public class AppConfig {
    @Bean("stubRepository")
    public TaskRepository stubTaskRepository() {
        return new StubTaskRepository();
    }
}