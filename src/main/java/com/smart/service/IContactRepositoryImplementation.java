package com.smart.service;

import com.smart.model.Contact;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IContactRepositoryImplementation {

    List<Contact> getContact();



}
