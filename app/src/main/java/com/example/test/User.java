package com.example.test;

public class User {
    private String email;
    private String password;
    private String first_name;
    private String last_name;
    private String id;



    public User(String email, String password, String first_name, String last_name, String id) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.id = id;

    }

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getId() {
        return id;
    }

}
