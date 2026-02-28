package com.mipt.bayandinamariya.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import java.time.LocalDateTime;
/**
 * Бин с областью видимости request.
 * Создаётся новый экземпляр для каждого HTTP-запроса.
 * Содержит уникальный идентификатор запроса и время начала обработки.
 * Используется для демонстрации работы scope=request.
 */

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestScopedBean {

    private static final Logger logger = LoggerFactory.getLogger(RequestScopedBean.class);
    private final String requestId;
    private final LocalDateTime requestStartTime;

    public RequestScopedBean() {
        this.requestId = java.util.UUID.randomUUID().toString();
        this.requestStartTime = LocalDateTime.now();
        logger.debug("RequestScopedBean created: requestId={}", requestId);
    }

    public String getRequestId() {
        return requestId;
    }

    public LocalDateTime getRequestStartTime() {
        return requestStartTime;
    }

    public long getProcessingDurationMs() {
        return java.time.Duration.between(requestStartTime, LocalDateTime.now()).toMillis();
    }
}