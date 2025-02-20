package com.microproject.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            User foundUser = user.get();
            return new org.springframework.security.core.userdetails.User(
                    foundUser.getEmail(),
                    foundUser.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Assign a default role
            );
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}