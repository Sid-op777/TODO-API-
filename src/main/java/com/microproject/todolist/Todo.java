package com.microproject.todolist;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "todos") //optional - if you don't specify, the table name will be "todo", defaults to the class name
//ignore Cannot resolve table 'todos', it happens because h2 is not a persistant database
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")  // Validation annotation //Is not an empty string ("") //Does not contain only whitespace.
    @Column(nullable = false) //ensures the title is not null in the database //This annotation enforces the non-null constraint at the database level.
    //@NotNull could also be used but this works' are the class level
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    private String description;

    // Constructors (default and parameterized), Getters, and Setters
    public Todo() {}

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}