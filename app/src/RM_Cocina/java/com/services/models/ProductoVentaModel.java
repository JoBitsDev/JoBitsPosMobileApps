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

public class ProductoVentaModel implements Serializable, Comparable<ProductoVentaModel> {

    private static final long serialVersionUID = 1L;
    private String pCod;
    private String nombre;
    private float precioVenta;
    private String descripcion;
    private String cocinacodCocina;
    private SeccionModel seccionnombreSeccion;
    private int cantidad = 1;

    public ProductoVentaModel() {
    }

    public ProductoVentaModel(String pCod) {
        this.pCod = pCod;
    }

    public ProductoVentaModel(String pCod, String nombre, float precioVenta) {
        this.pCod = pCod;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
    }

    public void setCantidad(int cantidad) {
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

    public String getCocinacodCocina() {
        return cocinacodCocina;
    }

    public void setCocinacodCocina(String cocinacodCocina) {
        this.cocinacodCocina = cocinacodCocina;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getpCod() {
        return pCod;
    }

    public void setpCod(String pCod) {
        this.pCod = pCod;
    }

    public SeccionModel getSeccionnombreSeccion() {
        return seccionnombreSeccion;
    }

    public void setSeccionnombreSeccion(SeccionModel seccionnombreSeccion) {
        this.seccionnombreSeccion = seccionnombreSeccion;
    }

    public void restarCantidad() {
        if (cantidad - 1 >= 0)
            cantidad--;
    }

    public void sumarCantida() {
        cantidad++;
    }

    public int getCantidad() {
        return cantidad;
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
        return nombre.compareTo(another.nombre);
    }

    public void increaceAmount() {
        cantidad++;
    }

    public void decreaseAmmount() {
        cantidad--;
    }
}
