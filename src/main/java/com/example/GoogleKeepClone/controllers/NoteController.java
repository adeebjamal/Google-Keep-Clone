package com.example.GoogleKeepClone.controllers;

import com.example.GoogleKeepClone.entities.Note;
import com.example.GoogleKeepClone.services.NoteService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping(path = "")
    public ResponseEntity<Map<String, String>> addNote(@RequestBody Note note, HttpServletRequest request) {
        Map<String, String> responseBody = new HashMap<>();
        try {
            Cookie[] cookies = request.getCookies();
            Cookie loggedInUser = null;
            if(cookies != null) {
                for(Cookie cookie: cookies) {
                    if("loggedInUser".equals(cookie.getName())) {
                        loggedInUser = cookie;
                        break;
                    }
                }
            }
            if(loggedInUser == null) {
                responseBody.put("Message", "You need to login first.");
                return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
            }
            return this.noteService.addNote(note, loggedInUser);
        }
        catch(Exception e) {
            System.out.println("NoteController : addNote");
            System.out.println(e.getMessage());
            responseBody.put("message", "Internal server error...");
            return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}