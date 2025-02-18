package com.microproject.todolist;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public Todo getTodoById(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        return todo.orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + id));
    }

    @Override
    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + id));

        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());

        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public Page<Todo> getAllTodos(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }


}