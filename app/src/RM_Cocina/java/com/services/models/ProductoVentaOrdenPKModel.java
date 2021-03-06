package com.services.models;

public class ProductoVentaOrdenPKModel {


    private String productoVentapCod;


    private String ordencodOrden;

    public ProductoVentaOrdenPKModel() {
    }

    public ProductoVentaOrdenPKModel(String productoVentapCod, String ordencodOrden) {
        this.productoVentapCod = productoVentapCod;
        this.ordencodOrden = ordencodOrden;
    }

    public String getProductoVentapCod() {
        return productoVentapCod;
    }

    public void setProductoVentapCod(String productoVentapCod) {
        this.productoVentapCod = productoVentapCod;
    }

    public String getOrdencodOrden() {
        return ordencodOrden;
    }

    public void setOrdencodOrden(String ordencodOrden) {
        this.ordencodOrden = ordencodOrden;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoVentapCod != null ? productoVentapCod.hashCode() : 0);
        hash += (ordencodOrden != null ? ordencodOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoVentaOrdenPKModel)) {
            return false;
        }
        ProductoVentaOrdenPKModel other = (ProductoVentaOrdenPKModel) object;
        if ((this.productoVentapCod == null && other.productoVentapCod != null) || (this.productoVentapCod != null && !this.productoVentapCod.equals(other.productoVentapCod))) {
            return false;
        }
        if ((this.ordencodOrden == null && other.ordencodOrden != null) || (this.ordencodOrden != null && !this.ordencodOrden.equals(other.ordencodOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.restManager.ProductoVentaOrdenPKModel[ productoVentapCod=" + productoVentapCod + ", ordencodOrden=" + ordencodOrden + " ]";
    }
    
}
