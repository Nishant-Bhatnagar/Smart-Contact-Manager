package com.smart.service;

import com.smart.model.Contact;
import com.smart.repository.IContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactRepositoryImplementation implements IContactRepositoryImplementation {

    @Autowired
    private IContactRepository iContactRepository;

    public List<Contact> getContact() {
        System.out.println("Insode");
        return iContactRepository.findAll();
    }


}
