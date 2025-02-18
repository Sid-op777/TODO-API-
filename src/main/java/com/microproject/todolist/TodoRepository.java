package com.microproject.todolist;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Nonnull Page<Todo> findAll(@Nonnull Pageable pageable);  // For pagination
}