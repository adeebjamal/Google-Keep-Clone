package com.example.GoogleKeepClone.Repositories;

import com.example.GoogleKeepClone.entities.Note;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NotesRepo extends JpaRepository<Note, Integer> {

    public static final String ADD_NOTE = "INSERT INTO NOTE (TITLE, CONTENT, USER_ID) VALUES (?, ?, ?)";
    @Modifying
    @Transactional
    @Query(value = ADD_NOTE, nativeQuery = true)
    public void insertNote(String title, String content, String userId);

}