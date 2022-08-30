/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.models.orden;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Jorge
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoVentaOrdenModel implements Serializable {

    private static final long serialVersionUID = 1L;
    protected int id;
    private Float enviadosacocina;
    private float cantidad;
    private String codOrden;
    private String codMesa;
    private String usuario;
    private Nota nota ;

    private ProductoVentaModel productoVenta;

    private String nombreProductoVendido;
    private float precioVendido;
    private int numeroComensal;


    public ProductoVentaOrdenModel() {
        nota = new Nota();

    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroComensal() {
        return numeroComensal;
    }

    public void setNumeroComensal(int numeroComensal) {
        this.numeroComensal = numeroComensal;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreProductoVendido() {
        return nombreProductoVendido;
    }

    public void setNombreProductoVendido(String nombreProductoVendido) {
        this.nombreProductoVendido = nombreProductoVendido;
    }

    public ProductoVentaModel getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(ProductoVentaModel productoVenta) {
        this.productoVenta = productoVenta;
    }

    public float getPrecioVendido() {
        return precioVendido;
    }

    public void setPrecioVendido(float precioVendido) {
        this.precioVendido = precioVendido;
    }

    public String getCodOrden() {
        return codOrden;
    }

    public void setCodOrden(String codOrden) {
        this.codOrden = codOrden;
    }

    public String getCodMesa() {
        return codMesa;
    }

    public void setCodMesa(String codMesa) {
        this.codMesa = codMesa;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "com.restManager.ProductoVentaOrdenModel[ productovOrdenPKModel=" + id + " ]";
    }

    public Float getEnviadosacocina() {
        return enviadosacocina;
    }

    public void setEnviadosacocina(Float enviadosacocina) {
        this.enviadosacocina = enviadosacocina;
    }


}
