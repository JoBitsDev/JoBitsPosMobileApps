/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.services.models;

/**
 * FirstDream
 * @author Jorge
 * 
 */
public class DpteListModel {

    private String usuario;
    private float monto,ordenesAtendidas,pagoPorVentas;

    public DpteListModel() {
    }

    public String getUsuario() {
        return usuario;
    }

    public float getMonto() {
        return monto;
    }

    public float getOrdenesAtendidas() {
        return ordenesAtendidas;
    }

    public float getPagoPorVentas() {
        return pagoPorVentas;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setOrdenesAtendidas(float ordenesAtendidas) {
        this.ordenesAtendidas = ordenesAtendidas;
    }

    public void setPagoPorVentas(float pagoPorVentas) {
        this.pagoPorVentas = pagoPorVentas;
    }
}
