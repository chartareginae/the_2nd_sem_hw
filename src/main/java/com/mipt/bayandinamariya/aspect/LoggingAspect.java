package com.mipt.bayandinamariya.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

/**
 * AOP-аспект для логирования вызовов методов сервиса.
 * Использует @Around advice для перехвата выполнения методов
 * в пакете service. Логирует начало, конец, параметры и результат
 * выполнения, а также время обработки и исключения.
 */

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.example.taskmanager.service..*)")
    public void serviceMethods() {}

    @Around("serviceMethods()")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();

        logger.info("Start {} with args: {}", methodName, Arrays.toString(args));

        long startTime = System.currentTimeMillis();
        Object result;

        try {
            result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;

            if (result != null) {
                logger.info("End {} in {}ms, result: {}", methodName, duration, result);
            } else {
                logger.info("End {} in {}ms, no result", methodName, duration);
            }
            return result;

        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logger.error("End {} in {}ms, exception: {}", methodName, duration, e.getMessage());
            throw e;
        }
    }
}