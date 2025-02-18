package com.microproject.todolist;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {
    Todo createTodo(Todo todo);
    Todo getTodoById(Long id);
    Todo updateTodo(Long id, Todo todo);
    void deleteTodo(Long id);
    Page<Todo> getAllTodos(Pageable pageable);

}