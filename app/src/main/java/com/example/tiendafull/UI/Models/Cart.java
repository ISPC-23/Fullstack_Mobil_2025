package com.example.tiendafull.UI.Models;

import java.util.List;

public class Cart {
    private int id;
    private String email;
    private String fecha_creacion;
    private List<CartDetail> items;

    public Cart(int id, String email, String fecha_creacion, List<CartDetail> items) {
        this.id = id;
        this.email = email;
        this.fecha_creacion = fecha_creacion;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public List<CartDetail> getItems() {
        return items;
    }

    public void setItems(List<CartDetail> items) {
        this.items = items;
    }
}