package com.smart.controller;

import com.smart.model.User;
import com.smart.repository.IContactRepository;
import com.smart.service.IContactRepositoryImplementation;
import com.smart.service.IUserRepositoryImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SearchController {
    @Autowired
    IContactRepositoryImplementation iContactRepositoryImplementation;
    @Autowired
    IUserRepositoryImplementation iUserRepositoryImplementation;

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal)
    {
        User user = iUserRepositoryImplementation.getUserLogin(principal.getName());
        return ResponseEntity.ok(iContactRepositoryImplementation.getSearchItems(query, user));
    }

}
