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
    private Float enviadosACocina;
    private float cantidad;
    private String codOrden;
    private String codMesa;
    private String nota;

    private ProductoVentaModel productoVenta;
    private int numeroComensal;


    public ProductoVentaOrdenModel() {
    }

    public ProductoVentaOrdenModel(int id) {
        this.id = id;
    }

    public ProductoVentaOrdenModel(int id, float cantidad) {
        this.id = id;
        this.cantidad = cantidad;
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

    public ProductoVentaModel getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(ProductoVentaModel productoVenta) {
        this.productoVenta = productoVenta;
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

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "com.restManager.ProductoVentaOrdenModel[ productovOrdenPKModel=" + id + " ]";
    }

    public Float getEnviadosACocina() {
        return enviadosACocina;
    }

    public void setEnviadosACocina(Float enviadosACocina) {
        this.enviadosACocina = enviadosACocina;
    }


}
