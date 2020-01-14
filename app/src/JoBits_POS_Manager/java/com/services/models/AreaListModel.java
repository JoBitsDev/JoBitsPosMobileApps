/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.models;

/**
 * JoBits
 *
 * @author Jorge
 */
public class AreaListModel {

    private String cod, nombre;
    private float ventaReal, ventaNeta;

    public AreaListModel() {
    }

    public AreaListModel(String cod, String nombre, float ventaReal, float ventaNeta) {
        this.cod = cod;
        this.nombre = nombre;
        this.ventaReal = ventaReal;
        this.ventaNeta = ventaNeta;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getVentaReal() {
        return ventaReal;
    }

    public void setVentaReal(float ventaReal) {
        this.ventaReal = ventaReal;
    }

    public float getVentaNeta() {
        return ventaNeta;
    }

    public void setVentaNeta(float ventaNeta) {
        this.ventaNeta = ventaNeta;
    }
}
