package com.example.tiendafull.UI.Models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("user")
    private User user;

    @SerializedName("token")
    private String token;

    @SerializedName("is_staff")
    private boolean is_staff;

    public LoginResponse(User user, String token, boolean is_staff) {
        this.user = user;
        this.token = token;
        this.is_staff = is_staff;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean is_staff() {
        return is_staff;
    }

    public void setIs_staff(boolean is_staff) {
        this.is_staff = is_staff;
    }
}