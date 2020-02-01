/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Jorge
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoVentaOrdenModel implements Serializable {

    private Float enviadosACocina;
    protected ProductoVentaOrdenPKModel productoVentaOrdenPK;
    private Float cantidad;
    private OrdenModel orden;
    private ProductoVentaModel productoVenta;
    private int numeroComensal;
    private MesaModel mesa;
    private String nota;

    public ProductoVentaOrdenPKModel getProductoVentaOrdenPK() {
        return productoVentaOrdenPK;
    }

    public void setProductoVentaOrdenPK(ProductoVentaOrdenPKModel productoVentaOrdenPK) {
        this.productoVentaOrdenPK = productoVentaOrdenPK;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public OrdenModel getOrden() {
        return orden;
    }

    public void setOrden(OrdenModel orden) {
        this.orden = orden;
    }

    public ProductoVentaModel getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(ProductoVentaModel productoVenta) {
        this.productoVenta = productoVenta;
    }

    public int getNumeroComensal() {
        return numeroComensal;
    }

    public void setNumeroComensal(int numeroComensal) {
        this.numeroComensal = numeroComensal;
    }

    public MesaModel getMesa() {
        return mesa;
    }

    public void setMesa(MesaModel mesa) {
        this.mesa = mesa;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoVentaOrdenPK != null ? productoVentaOrdenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoVentaOrdenModel)) {
            return false;
        }
        ProductoVentaOrdenModel other = (ProductoVentaOrdenModel) object;
        if (productoVenta.getPCod().equals(other.getProductoVenta().getPCod()) && orden.getCodOrden().equals(other.getOrden().getCodOrden())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "com.restManager.ProductoVentaOrdenModel[ productovOrdenPKModel=" + productoVentaOrdenPK + " ]";
    }

    public Float getEnviadosACocina() {
        return enviadosACocina;
    }

    public void setEnviadosACocina(Float enviadosACocina) {
        this.enviadosACocina = enviadosACocina;
    }

}
