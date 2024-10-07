package com.example.tiendafull.UI.Models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class User {
    @SerializedName("id")
    private String id;

    @SerializedName("email") // Usar email en lugar de username
    private String email;

    @SerializedName("username") // Usar email en lugar de username
    private String username;

    @SerializedName("first_name")
    private String firstName; // Cambiar a camelCase

    @SerializedName("last_name")
    private String lastName; // Cambiar a camelCase

    @SerializedName("nro_documento")
    private long nroDocumento; // Cambiar a camelCase

    @SerializedName("password")
    private String password;

    @SerializedName("is_admin")
    private Boolean isAdmin;

    @SerializedName("telefono") // Cambiar a "telefono" para que coincida con el backend
    private String phone;

    @SerializedName("last_connection")
    private Date lastConnection;


    // Constructor con parámetros, sin username
    public User(String email, String password, long nroDocumento, String phone, String firstName, String lastName) {
        this.email = email;
        this.username = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nroDocumento = nroDocumento;
        this.phone = phone;
        this.isAdmin = false; // Puedes ajustarlo según sea necesario
        this.lastConnection = new Date(); // Inicializar con la fecha actual
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(long nroDocumento) { // Cambiar el tipo a long
        this.nroDocumento = nroDocumento;
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

    public Date getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }
}
