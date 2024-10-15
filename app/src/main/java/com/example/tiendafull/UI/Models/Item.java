package com.example.tiendafull.UI.Models;

public class Item {
    private Products producto;
    private int cantidad;
    private int precio_compra;

    public Item(Products producto, int cantidad, int precio_compra) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio_compra = precio_compra;
    }

    public Products getProducto() {
        return producto;
    }

    public void setProducto(Products producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(int precio_compra) {
        this.precio_compra = precio_compra;
    }
}
