/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.io.Serializable;
/**
 *
 * @author Jorge
 */

public class ProductovOrden implements Serializable {


    private Float enviadosACocina;

    private static final long serialVersionUID = 1L;

    protected ProductovOrdenPK productovOrdenPK;

    private Float cantidad;

    private Orden orden;

    private ProductoVenta productoVenta;

    public int getNumero_comensal() {
        return numero_comensal;
    }

    public void setNumero_comensal(int numero_comensal) {
        this.numero_comensal = numero_comensal;
    }

    private int numero_comensal;

    private Mesa m;

    private String nota;

    public ProductovOrden() {
    }

    public ProductovOrden(ProductovOrdenPK productovOrdenPK) {
        this.productovOrdenPK = productovOrdenPK;
    }

    public ProductovOrden(ProductovOrdenPK productovOrdenPK, float cantidad) {
        this.productovOrdenPK = productovOrdenPK;
        this.cantidad = cantidad;
    }

    public ProductovOrden(String productoVentapCod, String ordencodOrden) {
        this.productovOrdenPK = new ProductovOrdenPK(productoVentapCod, ordencodOrden);
    }

    public ProductovOrdenPK getProductovOrdenPK() {
        return productovOrdenPK;
    }

    public void setProductovOrdenPK(ProductovOrdenPK productovOrdenPK) {
        this.productovOrdenPK = productovOrdenPK;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public ProductoVenta getProductoVenta() {
        return productoVenta;
    }

    public void setProductoVenta(ProductoVenta productoVenta) {
        this.productoVenta = productoVenta;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Mesa getM() {
        return m;
    }

    public void setM(Mesa m) {
        this.m = m;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productovOrdenPK != null ? productovOrdenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductovOrden) ) {
            return false;
        }
        ProductovOrden other = (ProductovOrden) object;
        if (productoVenta.getPCod().equals(other.getProductoVenta().getPCod()) && orden.getCodOrden().equals(other.getOrden().getCodOrden())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "com.restManager.ProductovOrden[ productovOrdenPK=" + productovOrdenPK + " ]";
    }

    public Float getEnviadosACocina() {
        return enviadosACocina;
    }

    public void setEnviadosACocina(Float enviadosACocina) {
        this.enviadosACocina = enviadosACocina;
    }
    
}
