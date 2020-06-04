package com.example.myapplication1;

import java.io.Serializable;

public class User implements Serializable {
    private String username, email, password, phone;

    public User(){

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone(){
        return password;
    }
}
