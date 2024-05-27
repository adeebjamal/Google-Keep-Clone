package com.example.GoogleKeepClone.services;

import com.example.GoogleKeepClone.Repositories.NotesRepo;
import com.example.GoogleKeepClone.Utilities.JwtUtility;
import com.example.GoogleKeepClone.entities.Note;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private NotesRepo notesRepo;

    @Override
    public ResponseEntity<Map<String, String>> addNote(Note note, Cookie loggedInUser) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            String userid = this.jwtUtility.validateToken(loggedInUser.getValue());
            this.notesRepo.insertNote(note.getTitle(), note.getContent(), userid);
            responseBody.put("User Id of loggedInUser", userid);
            responseBody.put("Title", note.getTitle());
            responseBody.put("Content", note.getContent());
            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
        }
        catch(Exception e) {
            System.out.println("NoteServiceImpl : addNote");
            System.out.println(e.getMessage());
            responseBody.put("Error", "Internal server error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> getNotesByLoggedInUser(String token) {
        Map<String, Object> responseBody = new HashMap<>();
        try {
            String userId = this.jwtUtility.validateToken(token);
            List<Note> notesByLoggedInUser = new ArrayList<>();
            notesByLoggedInUser = this.notesRepo.getNotesByLoggedInUser(userId);
            responseBody.put("USER_ID", userId);
            responseBody.put("NOTES", notesByLoggedInUser);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println("NoteServiceImpl : getNotesForLoggedInUser");
            System.out.println(e.getMessage());
            responseBody.put("Error", "Internal server error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> deleteNoteByLoggedInUser(String token, String noteId) {
        Map<String, Object> responseBody = new HashMap<>();
        try {
            String userId = this.jwtUtility.validateToken(token);
            Note foundNote = this.notesRepo.findByNoteIdAndUserId(noteId, userId);
            if(foundNote == null) {
                responseBody.put("Message", "Note with ID : " + noteId + " doesn't exist.");
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }
            this.notesRepo.deleteNoteByLoggedInUser(noteId, userId);
            responseBody.put("Message", "Note with ID : " + noteId + " was deleted successfully.");
            responseBody.put("Deleted note", foundNote);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println("NoteServiceImpl : deleteNoteByLoggedInUser");
            System.out.println(e.getMessage());
            responseBody.put("Error", "Internal server error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> updateNoteByLoggedInUser(String token, Note note, String noteId) {
        Map<String, Object> responseBody = new HashMap<>();
        try {
            String userId = this.jwtUtility.validateToken(token);
            Note foundNote = this.notesRepo.findByNoteIdAndUserId(noteId, userId);
            if(foundNote == null) {
                responseBody.put("Message", "Note with ID : " + noteId + " doesn't exist.");
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }
            String newTitle = note.getTitle() == null ? foundNote.getTitle() : note.getTitle();
            String newContent = note.getContent() == null ? foundNote.getContent() : note.getContent();
            this.notesRepo.updateNoteByLoggedInUser(newTitle, newContent, noteId, userId);

            responseBody.put("Message", "Note with ID : " + noteId + " was updated successfully.");
            responseBody.put("Previous value", foundNote);
            note.setId(Integer.parseInt(noteId));
            note.setUserId(Integer.parseInt(userId));
            responseBody.put("Updated value", note);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        }
        catch(Exception e) {
            System.out.println("NoteServiceImpl : updateNoteByLoggedInUser");
            System.out.println(e.getMessage());
            responseBody.put("Error", "Internal server error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
