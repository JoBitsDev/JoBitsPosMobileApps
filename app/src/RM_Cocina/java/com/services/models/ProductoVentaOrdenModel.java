/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.models;

import java.io.Serializable;
/**
 *
 * @author Jorge
 */

public class ProductoVentaOrdenModel implements Serializable {


    private Float enviadosACocina;

    private static final long serialVersionUID = 1L;

    protected ProductoVentaOrdenPKModel productovOrdenPK;

    private Float cantidad;

    private OrdenModel orden;

    private ProductoVentaModel productoVenta;

    public int getNumero_comensal() {
        return numero_comensal;
    }

    public void setNumero_comensal(int numero_comensal) {
        this.numero_comensal = numero_comensal;
    }

    private int numero_comensal;

    private MesaModel m;

    private String nota;

    public ProductoVentaOrdenModel() {
    }

    public ProductoVentaOrdenModel(ProductoVentaOrdenPKModel productovOrdenPK) {
        this.productovOrdenPK = productovOrdenPK;
    }

    public ProductoVentaOrdenModel(ProductoVentaOrdenPKModel productovOrdenPK, float cantidad) {
        this.productovOrdenPK = productovOrdenPK;
        this.cantidad = cantidad;
    }

    public ProductoVentaOrdenModel(String productoVentapCod, String ordencodOrden) {
        this.productovOrdenPK = new ProductoVentaOrdenPKModel(productoVentapCod, ordencodOrden);
    }

    public ProductoVentaOrdenPKModel getProductovOrdenPK() {
        return productovOrdenPK;
    }

    public void setProductovOrdenPK(ProductoVentaOrdenPKModel productovOrdenPK) {
        this.productovOrdenPK = productovOrdenPK;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
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

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public MesaModel getM() {
        return m;
    }

    public void setM(MesaModel m) {
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
        if (!(object instanceof ProductoVentaOrdenModel) ) {
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
        return "com.restManager.ProductoVentaOrdenModel[ productovOrdenPKModel=" + productovOrdenPK + " ]";
    }

    public Float getEnviadosACocina() {
        return enviadosACocina;
    }

    public void setEnviadosACocina(Float enviadosACocina) {
        this.enviadosACocina = enviadosACocina;
    }
    
}
