package com.example.tiendafull.UI.Models;

public class DeleteProductRequest {
    private int item_id;


    public DeleteProductRequest(int item_id) {
        this.item_id = item_id;

    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

}