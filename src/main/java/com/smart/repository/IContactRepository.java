package com.smart.repository;

import com.smart.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  IContactRepository extends JpaRepository<Contact, Integer> {
    public Page<Contact> findByUser_Id(int userId, Pageable pegable);
    public Contact findById(int cId);
}
