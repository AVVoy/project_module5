package com.example.project_module5.service;

import com.example.project_module5.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User save(User user);

    List<User> findAll();


    User create(User user);

    User getByUsername(String username);

    User getCurrentUser();

    UserDetailsService userDetailsService();


//    boolean login(User user);
}
