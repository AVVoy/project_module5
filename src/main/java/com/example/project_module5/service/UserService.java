package com.example.project_module5.service;

import com.example.project_module5.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User save(User user);

    User create(User user);

    User getByUsername(String username);

    User getCurrentUser();

}
