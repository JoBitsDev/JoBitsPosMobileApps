/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge
 */
public class Seccion implements Serializable, Comparable<Seccion>{

    private static final long serialVersionUID = 1L;
    private String nombreSeccion;
    private String descripcion;
    private String cartacodCarta;
    private List<ProductoVenta> productos = new ArrayList<ProductoVenta>();

    public Seccion() {
    }

    public Seccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public Seccion(String nombreSeccion, String cartacodCarta) {
        this.nombreSeccion = nombreSeccion;
        this.cartacodCarta = cartacodCarta;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCartacodCarta() {
        return cartacodCarta;
    }

    public void setCartacodCarta(String cartacodCarta) {
        this.cartacodCarta = cartacodCarta;
    }


    public List<ProductoVenta> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoVenta> productos) {
        this.productos = productos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreSeccion != null ? nombreSeccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Seccion)) {
            return false;
        }
        Seccion other = (Seccion) object;
        if ((this.nombreSeccion == null && other.nombreSeccion != null) || (this.nombreSeccion != null && !this.nombreSeccion.equals(other.nombreSeccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.main.objetos.Seccion[ nombreSeccion=" + nombreSeccion + " ]";
    }

    @Override
    public int compareTo(Seccion another) {
        return nombreSeccion.compareTo(another.nombreSeccion);
    }

    public void addProducto(ProductoVenta x) {
        if(!productos.contains(x)){
            productos.add(x);
        }
    }
}
