package com.smart.service;

import com.smart.model.Contact;
import com.smart.model.User;
import com.smart.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRepositoryImplementation implements IUserRepositoryImplementation {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public List<User> fetchUsers() {
        return iUserRepository.findAll();
    }

    @Override
    public User getUserLogin(String email) {

        return iUserRepository.findByEmail(email);
    }

    @Override
    public String addUser(User user) {

        user.setRole("ROLE_USER");
        user.setUserActive(true);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        iUserRepository.save(user);
        return "added successfully";
    }

    @Override
    public String addContact(User user, Contact contact) {
        contact.setUser(user);
        user.getContacts().add(contact);
//        System.out.println(contact);
        iUserRepository.save(user);
        return "added successfully";
    }
}
