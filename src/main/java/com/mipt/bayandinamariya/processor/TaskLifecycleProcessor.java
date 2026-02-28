package com.mipt.bayandinamariya.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import com.mipt.bayandinamariya.service.TaskService;
import com.mipt.bayandinamariya.repository.TaskRepository;

/**
 * Processer для отслеживания жизненного цикла бинов TaskService и TaskRepository.
 * Реализует интерфейс BeanPostProcessor для логирования этапов
 * создания и инициализации целевых бинов. Используется для
 * демонстрации понимания механизма жизненного цикла Spring.
 */

@Component
public class TaskLifecycleProcessor implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TaskLifecycleProcessor.class);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        if (bean instanceof TaskService) {
            logger.info("[BeanPostProcessor] Before init: TaskService bean '{}'", beanName);
        } else if (bean instanceof TaskRepository) {
            logger.info("[BeanPostProcessor] Before init: TaskRepository bean '{}'", beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

        if (bean instanceof TaskService) {
            logger.info("[BeanPostProcessor] After init: TaskService bean '{}' - READY", beanName);
        } else if (bean instanceof TaskRepository) {
            logger.info("[BeanPostProcessor] After init: TaskRepository bean '{}' - READY", beanName);
        }
        return bean;
    }
}