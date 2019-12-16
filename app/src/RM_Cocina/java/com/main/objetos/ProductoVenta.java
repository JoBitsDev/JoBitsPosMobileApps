/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jorge
 */
public class ProductoVenta implements Serializable,Comparable<ProductoVenta> {

    private static final long serialVersionUID = 1L;
    private String pCod;
    private String nombre;
    private float precioVenta;
    private String descripcion;
    private String cocinacodCocina;
    private String seccionnombreSeccion;
    private int cantidad = 1;

    public ProductoVenta() {
    }

    public ProductoVenta(String pCod) {
        this.pCod = pCod;
    }

    public ProductoVenta(String pCod, String nombre, float precioVenta) {
        this.pCod = pCod;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
    }

    public ProductoVenta(String cocinacodCocina, String nombre, String pCod,
                         float precioVenta, String seccionnombreSeccion) {
        this.cocinacodCocina = cocinacodCocina;
        this.nombre = nombre;
        this.pCod = pCod;
        this.precioVenta = precioVenta;
        this.seccionnombreSeccion = seccionnombreSeccion;
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

    public String getSeccionnombreSeccion() {
        return seccionnombreSeccion;
    }

    public void setSeccionnombreSeccion(String seccionnombreSeccion) {
        this.seccionnombreSeccion = seccionnombreSeccion;
    }


    public void restarCantidad() {
        if(cantidad-1 >= 0)
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
        if (!(object instanceof ProductoVenta)) {
            return false;
        }
        ProductoVenta other = (ProductoVenta) object;
        if ((this.pCod == null && other.pCod != null) || (this.pCod != null && !this.pCod.equals(other.pCod))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.main.objetos.ProductoVenta[ pCod=" + pCod + " ]";
    }

    @Override
    public int compareTo(ProductoVenta another) {
        return nombre.compareTo(another.nombre);
    }

    public void increaceAmount() {
        cantidad++;
    }

    public void decreaseAmmount() {
        cantidad--;
    }
}
