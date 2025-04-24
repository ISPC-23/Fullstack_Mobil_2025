package com.example.tiendafull.UI.Models;

public class CancelPurchaseRequest {

    private boolean es_cancelada = true;

    public CancelPurchaseRequest(boolean es_cancelada) {

        this.es_cancelada = es_cancelada;
    }

    public boolean isEs_cancelada() {
        return es_cancelada;
    }

    public void setEs_cancelada(boolean es_cancelada) {
        this.es_cancelada = es_cancelada;
    }
}
