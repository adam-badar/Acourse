package com.example.test;

import java.util.ArrayList;

public class FetchUser {

    public String email;
    public String first_name;
    public String id;
    public String last_name;
    public String password;

    public FetchUser(){

    }

    public FetchUser(String email, String first_name, String id, String last_name, String password)
    {
        this.email = email;
        this.first_name = first_name;
        this.id = id;
        this.last_name = last_name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getId() { return id; }

    public String getLastName() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }
}