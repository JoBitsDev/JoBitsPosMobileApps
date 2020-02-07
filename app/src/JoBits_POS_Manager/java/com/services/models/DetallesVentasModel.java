package com.services.models;

public class DetallesVentasModel {
    private String nombreProducto;
    private String productoCod;
    private float cantidad;
    private String precioVenta;
    private String montoVenta;

    public DetallesVentasModel() {
    }

    public DetallesVentasModel(String nombreProducto, String productoCod, float cantidad, String precioVenta, String montoVenta) {
        this.nombreProducto = nombreProducto;
        this.productoCod = productoCod;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
        this.montoVenta = montoVenta;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getProductoCod() {
        return productoCod;
    }

    public void setProductoCod(String productoCod) {
        this.productoCod = productoCod;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getMontoVenta() {
        return montoVenta;
    }

    public void setMontoVenta(String montoVenta) {
        this.montoVenta = montoVenta;
    }
}
