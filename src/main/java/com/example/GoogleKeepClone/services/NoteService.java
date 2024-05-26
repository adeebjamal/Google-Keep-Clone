package com.example.GoogleKeepClone.services;

import com.example.GoogleKeepClone.entities.Note;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface NoteService {

    ResponseEntity<Map<String, String>> addNote(Note note, Cookie loggedInUser);

}