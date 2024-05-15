package com.example.GoogleKeepClone.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "REGISTERED_USER")
public class RegisteredUser {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    public int getId() {
        // get id
        return this.id;
    }

    public void setId(int id) {
        // set id
        this.id = id;
    }

    public String getName() {
        // get name
        return this.name;
    }

    public void setName(String name) {
        // set name
        this.name = name;
    }

    public String getEmail() {
        // get email
        return this.email;
    }

    public void setEmail(String email) {
        // set email
        this.email = email;
    }

    public String getPassword() {
        // get password
        return this.password;
    }

    public void setPassword(String password) {
        // set password
        this.password = password;
    }
}
