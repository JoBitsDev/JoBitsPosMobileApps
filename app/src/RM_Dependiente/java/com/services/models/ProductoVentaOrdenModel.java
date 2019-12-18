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

    protected ProductovOrdenPKModel productovOrdenPKModel;

    private float cantidad;

    private OrdenModel ordenModel;

    private ProductoVentaModel productoVentaModel;

    public int getNumero_comensal() {
        return numero_comensal;
    }

    public void setNumero_comensal(int numero_comensal) {
        this.numero_comensal = numero_comensal;
    }

    private int numero_comensal;

    public ProductoVentaOrdenModel() {
    }

    public ProductoVentaOrdenModel(ProductovOrdenPKModel productovOrdenPKModel) {
        this.productovOrdenPKModel = productovOrdenPKModel;
    }

    public ProductoVentaOrdenModel(ProductovOrdenPKModel productovOrdenPKModel, float cantidad) {
        this.productovOrdenPKModel = productovOrdenPKModel;
        this.cantidad = cantidad;
    }

    public ProductoVentaOrdenModel(String productoVentapCod, String ordencodOrden) {
        this.productovOrdenPKModel = new ProductovOrdenPKModel(productoVentapCod, ordencodOrden);
    }

    public ProductovOrdenPKModel getProductovOrdenPKModel() {
        return productovOrdenPKModel;
    }

    public void setProductovOrdenPKModel(ProductovOrdenPKModel productovOrdenPKModel) {
        this.productovOrdenPKModel = productovOrdenPKModel;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public OrdenModel getOrdenModel() {
        return ordenModel;
    }

    public void setOrdenModel(OrdenModel ordenModel) {
        this.ordenModel = ordenModel;
    }

    public ProductoVentaModel getProductoVentaModel() {
        return productoVentaModel;
    }

    public void setProductoVentaModel(ProductoVentaModel productoVentaModel) {
        this.productoVentaModel = productoVentaModel;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productovOrdenPKModel != null ? productovOrdenPKModel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoVentaOrdenModel) ) {
            return false;
        }
        ProductoVentaOrdenModel other = (ProductoVentaOrdenModel) object;
        if (productoVentaModel.getPCod().equals(other.getProductoVentaModel().getPCod()) && ordenModel.getCodOrden().equals(other.getOrdenModel().getCodOrden())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "com.restManager.ProductoVentaOrdenModel[ productovOrdenPKModel=" + productovOrdenPKModel + " ]";
    }

    public Float getEnviadosACocina() {
        return enviadosACocina;
    }

    public void setEnviadosACocina(Float enviadosACocina) {
        this.enviadosACocina = enviadosACocina;
    }

    
}