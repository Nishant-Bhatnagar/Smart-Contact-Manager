package com.smart.service;

import com.smart.model.Contact;
import com.smart.model.User;

import java.util.List;

public interface IUserRepositoryImplementation {

    List<User> fetchUsers();

    User getUserLogin(String email);

    String addUser(User user);

    String addContact(User user,Contact contact);

}
