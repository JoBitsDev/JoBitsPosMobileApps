/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.models;

import java.io.Serializable;

/**
 * @author Jorge
 */
public class ProductoVentaModel implements Serializable, Comparable<ProductoVentaModel> {

    private static final long serialVersionUID = 1L;
    private String pCod;
    private String nombre;
    private float precioVenta;
    private String descripcion;
    private float cantidad = 1;
    private String seccionnombreSeccion;

    public ProductoVentaModel() {
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getPCod() {
        return pCod;
    }

    public void setPCod(String pCod) {
        this.pCod = pCod;
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

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getCantidad() {
        return cantidad;
    }

    public String getSeccionnombreSeccion() {
        return seccionnombreSeccion;
    }

    public void setSeccionnombreSeccion(String seccionnombreSeccion) {
        this.seccionnombreSeccion = seccionnombreSeccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pCod != null ? pCod.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProductoVentaModel)) {
            return false;
        }
        ProductoVentaModel other = (ProductoVentaModel) object;
        if ((this.pCod == null && other.pCod != null) || (this.pCod != null && !this.pCod.equals(other.pCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.services.objetos.ProductoVentaModel[ pCod=" + pCod + " ]";
    }

    @Override
    public int compareTo(ProductoVentaModel another) {
        return nombre.compareToIgnoreCase(another.nombre);
    }

    public void increaceAmount() {
        cantidad++;
    }

    public void decreaseAmmount() {
        cantidad--;
    }
}
