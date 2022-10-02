package com.smart.service;

import com.smart.model.Contact;
import com.smart.model.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IContactRepositoryImplementation {

    Page<Contact> getContact(int userId, int page);
    Contact getContactDetail(int cId);

    void deleteContactDetails(int cId);


    List<Contact> getSearchItems(String query, User user);

}
