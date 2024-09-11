package com.example.tiendafull.UI.Data;

public class Products {
    private String modelo;
    private double precio;
    private int stock;
    private String imagen;
    private String detalle;
    private String marca;
    private int rodado;
    private String estilo;
    private String material;
    private String color;

    public Products(String modelo, double precio, int stock, String imagen, String detalle, String marca, int rodado, String estilo, String material, String color) {
        this.modelo = modelo != null ? modelo : "Modelo por defecto";
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.detalle = detalle;
        this.marca = marca;
        this.rodado = rodado;
        this.estilo = estilo;
        this.material = material;
        this.color = color;
    }

    public String getModelo() {
        return modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDetalle() {
        return detalle;
    }

    public String getMarca() {
        return marca;
    }

    public int getRodado() {
        return rodado;
    }

    public String getEstilo() {
        return estilo;
    }

    public String getMaterial() {
        return material;
    }

    public String getColor() {
        return color;
    }

}