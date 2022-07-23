package com.smart.service;

import com.smart.model.User;
import com.smart.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IUserRepositoryImplementation{

    public List<User> fetchUsers();
        
}
