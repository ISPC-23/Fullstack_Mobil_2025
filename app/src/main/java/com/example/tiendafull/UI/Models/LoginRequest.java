package com.example.tiendafull.UI.Models;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("username")
    private String username;


    @SerializedName("password")
    private String password;

    public LoginRequest(String username , String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String username) {
        this.username= username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}