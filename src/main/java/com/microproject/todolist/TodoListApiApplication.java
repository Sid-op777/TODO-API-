package com.microproject.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//exclude removes the default login page that pops ups and also allows access to the endpoints which otherwise would be not authorized
public class TodoListApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoListApiApplication.class, args);
    }

}