package com.example.GoogleKeepClone.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "NOTE")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "USER_ID")
    private int userId;

    public int getId() {
        // get id
        return this.id;
    }

    public void setId(int id) {
        // set id
        this.id = id;
    }

    public String getTitle() {
        // get title
        return this.title;
    }

    public void setTitle(String title) {
        // set title
        this.title = title;
    }

    public String getContent() {
        // get content
        return this.content;
    }

    public void setContent(String content) {
        // set content
        this.content = content;
    }

    public int getUserId() {
        // get userId
        return userId;
    }

    public void setUserId(int userId) {
        // set userId
        this.userId = userId;
    }
}
