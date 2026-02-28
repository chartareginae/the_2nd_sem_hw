package com.mipt.bayandinamariya.config;

import com.mipt.bayandinamariya.scope.PrototypeScopedBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Конфигурация для бинов с различными областями видимости (scope).
 * Определяет prototype-бины для демонстрации создания новых
 * экземпляров при каждом обращении из контекста.
 */

@Configuration
public class ScopeConfig {

    @Bean
    @Scope("prototype")
    public PrototypeScopedBean prototypeBean() {
        return new PrototypeScopedBean();
    }
}