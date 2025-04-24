package com.example.tiendafull.UI.Models;

public class DeleteProductResponse {
    String message;

    public DeleteProductResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}