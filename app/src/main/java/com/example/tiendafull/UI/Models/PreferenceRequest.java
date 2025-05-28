package com.example.tiendafull.UI.Models;

import java.util.List;

public class PreferenceRequest {
    private List<PreferenceItem> items;

    public PreferenceRequest(List<PreferenceItem> items) {
        this.items = items;
    }

    public List<PreferenceItem> getItems() {
        return items;
    }
    public void setItems(List<PreferenceItem> items) {
        this.items = items;
    }
}