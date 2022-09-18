package com.smart.service;

import com.smart.model.Contact;
import com.smart.repository.IContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Contact> getContact(int userId, int page) {

        Pageable pageable = PageRequest.of(page,5);
        return iContactRepository.findByUser_Id(userId,pageable);
    }

    public Contact getContactDetail(int cId){
        return iContactRepository.findById(cId);
    }



}
