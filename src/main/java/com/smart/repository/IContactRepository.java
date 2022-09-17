package com.smart.repository;

import com.smart.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IContactRepository extends JpaRepository<Contact, Integer> {
    public List<Contact> findByUser_Id(int userId);
}
