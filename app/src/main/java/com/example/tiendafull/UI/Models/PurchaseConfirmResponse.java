package com.example.tiendafull.UI.Models;

import java.util.List;

public class PurchaseConfirmResponse {
    private String message;
    private Purchase purchase;
    private List<Item> details;

    public PurchaseConfirmResponse(String message, Purchase purchase, List<Item> details) {
        this.message = message;
        this.purchase = purchase;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public List<Item> getDetails() {
        return details;
    }

    public void setDetails(List<Item> details) {
        this.details = details;
    }
}
