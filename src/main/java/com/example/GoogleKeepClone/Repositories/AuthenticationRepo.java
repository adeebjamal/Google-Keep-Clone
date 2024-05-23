package com.example.GoogleKeepClone.Repositories;

import com.example.GoogleKeepClone.entities.RegisteredUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepo extends JpaRepository<RegisteredUser, Integer> {

    public RegisteredUser findByEmail(String email);

    public static final String INSERT_REGISTERED_USER = "INSERT INTO REGISTERED_USER (EMAIL, NAME, PASSWORD) VALUES (?, ?, ?)";
    @Modifying
    @Transactional
    @Query(value = INSERT_REGISTERED_USER, nativeQuery = true)
    public void insertRegisteredUser(String email, String name, String password);

}