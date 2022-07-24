package com.smart.service;

import com.smart.model.User;
import com.smart.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepositoryImplementation implements IUserRepositoryImplementation {

    @Autowired
    private IUserRepository iUserRepository;

    public List<User> fetchUsers() {
        return iUserRepository.findAll();
    }

    @Override
    public String addUser(User user) {
        user.setRole("ROLE_USER");
        user.setUserActive(true);
        iUserRepository.save(user);
        return "added successfully";
    }
}
