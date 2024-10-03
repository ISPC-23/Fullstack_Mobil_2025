package com.example.tiendafull.UI.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("identification_number")
    private long identificationNumber;

    @SerializedName("password")
    private String password;

    @SerializedName("is_admin")
    private Boolean isAdmin;

    @SerializedName("phone")
    private String phone;

    @SerializedName("adress")
    private String address;

    @SerializedName("last_connection")
    private Date lastConnection;


    public User() {}

    public User(String username, String email, String password, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = false;
        this.identificationNumber = 1115143242;
        this.phone = "111111";
        this.address = address;
        this.lastConnection = new Date();
    }

    // Getters y setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(Integer identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }
}
