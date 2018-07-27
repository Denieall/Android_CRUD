package com.denieall.crud.Model;

public class User {

    private String first_name;
    private String last_name;
    private String email;

    public User(String first_name, String last_name, String email) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;

    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }



}
