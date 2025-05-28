package com.example.tiendafull.UI.Models;

public class PreferenceItem {
    private String title;
    private int quantity;
    private double unit_price;

    public PreferenceItem(String title, int quantity, double unit_price) {
        this.title = title;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }
}