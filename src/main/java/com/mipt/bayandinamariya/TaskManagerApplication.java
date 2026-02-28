package com.mipt.bayandinamariya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Главный класс приложения Task Manager.
 * Точка входа Spring Boot приложения.
 * Содержит метод main и аннотацию @SpringBootApplication.
 * Включает поддержку AspectJ прокси через @EnableAspectJAutoProxy.
 */

@SpringBootApplication
@EnableAspectJAutoProxy
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}