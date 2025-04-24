package com.example.tiendafull.UI.Models;

import java.util.List;

public class CartDetail {

    private int  id;
    private int cantidad;

    private Products producto;

    public CartDetail(int id, int cantidad, Products producto) {
        this.id = id;
        this.cantidad = cantidad;

        this.producto = producto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public Products getProducto() {
        return producto;
    }

    public void setProducto(Products producto) {
        this.producto = producto;
    }
}
