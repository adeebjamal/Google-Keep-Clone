package com.example.GoogleKeepClone.Repositories;

import com.example.GoogleKeepClone.entities.Note;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotesRepo extends JpaRepository<Note, Integer> {

    public static final String ADD_NOTE = "INSERT INTO NOTE (TITLE, CONTENT, USER_ID) VALUES (?, ?, ?)";
    @Modifying
    @Transactional
    @Query(value = ADD_NOTE, nativeQuery = true)
    public void insertNote(String title, String content, String userId);

    public static final String GET_NOTES_FOR_LOGGED_IN_USER = "SELECT * FROM NOTE WHERE USER_ID = ?";
    @Query(value = GET_NOTES_FOR_LOGGED_IN_USER, nativeQuery = true)
    List<Note> getNotesByLoggedInUser(String userId);

}
