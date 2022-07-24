package com.smart.service;

import com.smart.model.User;

import java.util.List;

public interface IUserRepositoryImplementation {

    List<User> fetchUsers();

    String addUser(User user);

}
