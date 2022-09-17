package com.smart.service;

import com.smart.model.Contact;
import com.smart.repository.IContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
