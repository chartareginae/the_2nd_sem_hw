package com.mipt.bayandinamariya.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Бин с областью видимости prototype.
 * Создаётся новый экземпляр при каждом обращении из контекста.
 * Содержит генератор уникальных идентификаторов для задач на основе UUID.
 * Используется для демонстрации работы scope=prototype.
 */

@Component
@Scope("prototype")
public class PrototypeScopedBean {

    private static final Logger logger = LoggerFactory.getLogger(PrototypeScopedBean.class);
    private final String instanceId;

    public PrototypeScopedBean() {
        this.instanceId = java.util.UUID.randomUUID().toString();
        logger.debug("PrototypeScopedBean created: instanceId={}", instanceId);
    }

    public String generateTaskId() {
        return "TASK-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getInstanceId() {
        return instanceId;
    }
}