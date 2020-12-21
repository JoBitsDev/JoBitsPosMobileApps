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
public class ProductoVentaModel implements Serializable, Comparable<ProductoVentaModel> {

    private static final long serialVersionUID = 1L;
    private String codigoProducto;
    private String nombre;
    private float precioVenta;
    private String descripcion;
    private SeccionModel seccionnombreSeccion;

    public ProductoVentaModel() {
    }

    public String getPCod() {
        return codigoProducto;
    }

    public void setPCod(String pCod) {
        this.codigoProducto = pCod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public SeccionModel getSeccionnombreSeccion() {
        return seccionnombreSeccion;
    }

    public void setSeccionnombreSeccion(SeccionModel seccionnombreSeccion) {
        this.seccionnombreSeccion = seccionnombreSeccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoProducto != null ? codigoProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProductoVentaModel)) {
            return false;
        }
        ProductoVentaModel other = (ProductoVentaModel) object;
        if ((this.codigoProducto == null && other.codigoProducto != null) || (this.codigoProducto != null && !this.codigoProducto.equals(other.codigoProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.services.objetos.ProductoVentaModel[ codigoProducto=" + codigoProducto + " ]";
    }

    @Override
    public int compareTo(ProductoVentaModel another) {
        return nombre.compareToIgnoreCase(another.nombre);
    }
}
