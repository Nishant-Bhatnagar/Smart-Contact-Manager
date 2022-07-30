package com.smart.repository;

import com.smart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
//    @Query("select u from User u where w.email = : email")
//    public User getUserNameByUserEmail(@Param("email") String email);\

    User findByEmail(String email);
}
