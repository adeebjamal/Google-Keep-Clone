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

    public static final String FIND_BY_NOTE_ID_AND_USER_ID = "SELECT * FROM NOTE WHERE ID = ? AND USER_ID = ?";
    @Query(value = FIND_BY_NOTE_ID_AND_USER_ID, nativeQuery = true)
    Note findByNoteIdAndUserId(String noteId, String userId);

    public static final String DELETE_NOTE_BY_LOGGED_IN_USER = "DELETE FROM NOTE WHERE ID = ? AND USER_ID = ?";
    @Modifying
    @Transactional
    @Query(value = DELETE_NOTE_BY_LOGGED_IN_USER, nativeQuery = true)
    void deleteNoteByLoggedInUser(String noteId, String userId);

}
