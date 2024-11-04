package com.example.tiendafull.UI.Models;

public class CancelPurchaseResponse {
    private String message;

    public CancelPurchaseResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
