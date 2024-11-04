package com.example.tiendafull.UI.Models;

import java.util.List;

public class Purchase {
    private int id;
    private String nro_factura;
    private String email;
    private String modo_pago;
    private int total;
    private String fecha;
    private List<Item> detalle;
    private boolean es_cancelada;

    public int getId() {
        return id;
    }

    public boolean isEs_cancelada() {
        return es_cancelada;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNro_factura() {
        return nro_factura;
    }

    public void setNro_factura(String nro_factura) {
        this.nro_factura = nro_factura;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getModo_pago() {
        return modo_pago;
    }

    public void setModo_pago(String modo_pago) {
        this.modo_pago = modo_pago;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Item> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<Item> detalle) {
        this.detalle = detalle;
    }

    public Purchase(int id, String nro_factura, String email, String modo_pago, int total, String fecha, List<Item> detalle, boolean es_cancelada) {
        this.id = id;
        this.nro_factura = nro_factura;
        this.email = email;
        this.modo_pago = modo_pago;
        this.total = total;
        this.fecha = fecha;
        this.detalle = detalle;
        this.es_cancelada = false;



    }
}
