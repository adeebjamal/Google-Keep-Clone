package com.example.GoogleKeepClone.Repositories;

import com.example.GoogleKeepClone.entities.RegisteredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepo extends JpaRepository<RegisteredUser, Integer> {

    public RegisteredUser findByEmail(String email);

}
