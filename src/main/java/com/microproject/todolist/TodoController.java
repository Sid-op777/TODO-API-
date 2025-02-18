package com.microproject.todolist;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    //@Valid: This annotation tells Spring to validate the Todo object before it's passed to the createTodo or updateTodo method. If the validation fails, a MethodArgumentNotValidException will be thrown.
    @PostMapping
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
        Todo createdTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        try {
            Todo todo = todoService.getTodoById(id);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Valid @RequestBody Todo todo) {
        Todo updatedTodo = todoService.updateTodo(id, todo);
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<Page<Todo>> getAllTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Todo> todos = todoService.getAllTodos(pageable);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    /**
     * Exception handler responsible for handling validation errors.

     * This method is triggered when validation fails, such as when a required field (e.g., title) is blank.
     * In such cases, Spring throws a {@link MethodArgumentNotValidException}.

     * The exception is caught by this handler, which processes the validation errors and returns them in a map.
     * The map contains error messages where the key is the field name and the value is the error message.

     * The response will have a HTTP 400 Bad Request status code, indicating that the client sent invalid data.
     *
     * @param ex The {@link MethodArgumentNotValidException} that contains details of the validation failure.
     * @return A map of error messages, where the key is the field name and the value is the associated error message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Exception handler responsible for handling invalid JSON request bodies.

     * This method is triggered when the request body is not a valid JSON, causing Spring to throw an
     * {@link HttpMessageNotReadableException}.

     * The handler processes the exception and returns a 400 Bad Request status code, indicating that the
     * request body is malformed or unreadable. A generic error message is included in the response to inform
     * the client about the invalid JSON format.
     *
     * @param ex The {@link HttpMessageNotReadableException} that contains details of the invalid request body.
     * @return A {@link ResponseEntity} containing a generic error message with a 400 Bad Request status code.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Invalid request body format. Please provide a valid JSON.");
    }
}