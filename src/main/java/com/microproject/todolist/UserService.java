package com.microproject.todolist;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> findUserByEmail(String email);
}