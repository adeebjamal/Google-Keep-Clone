package com.example.GoogleKeepClone.services;

import com.example.GoogleKeepClone.entities.Note;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface NoteService {

    ResponseEntity<Map<String, String>> addNote(Note note, Cookie loggedInUser);

    ResponseEntity<Map<String, Object>> getNotesByLoggedInUser(String token);

    ResponseEntity<Map<String, Object>> deleteNoteByLoggedInUser(String token, String noteId);

    ResponseEntity<Map<String, Object>> updateNoteByLoggedInUser(String token, Note note, String noteId);

}
