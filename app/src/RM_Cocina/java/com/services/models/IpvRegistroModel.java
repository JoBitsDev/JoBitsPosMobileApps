/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.services.models;

import java.io.Serializable;

/**
 * FirstDream
 *
 * @author Jorge
 */

public class IpvRegistroModel implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation

    private Float inicio;
    private Float entrada;
    private Float disponible;
    private Float consumo;
    private Float consumoReal;
    private Float final1;
    private Float finalReal;

    public IpvRegistroModel() {
    }

    public IpvRegistroModel(String nombre) {
        this.nombre = nombre;
    }

    public IpvRegistroModel(String nombre, Float inicio, Float entrada, Float disponible,
                            Float consumo, Float consumoReal, Float final1, Float finalReal) {
        this.nombre = nombre;
        this.inicio = inicio;
        this.entrada = entrada;
        this.disponible = disponible;
        this.consumo = consumo;
        this.consumoReal = consumoReal;
        this.final1 = final1;
        this.finalReal = finalReal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getInicio() {
        return inicio;
    }

    public void setInicio(Float inicio) {
        this.inicio = inicio;
    }

    public Float getEntrada() {
        return entrada;
    }

    public void setEntrada(Float entrada) {
        this.entrada = entrada;
    }

    public Float getDisponible() {
        return disponible;
    }

    public void setDisponible(Float disponible) {
        this.disponible = disponible;
    }

    public Float getConsumo() {
        return consumo;
    }

    public void setConsumo(Float consumo) {
        this.consumo = consumo;
    }

    public Float getConsumoReal() {
        return consumoReal;
    }

    public void setConsumoReal(Float consumoReal) {
        this.consumoReal = consumoReal;
    }

    public Float getFinal1() {
        return final1;
    }

    public void setFinal1(Float final1) {
        this.final1 = final1;
    }

    public Float getFinalReal() {
        return finalReal;
    }

    public void setFinalReal(Float finalReal) {
        this.finalReal = finalReal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpvRegistroModel)) {
            return false;
        }
        IpvRegistroModel other = (IpvRegistroModel) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public java.lang.String toString() {
        return "com.restmanager.IpvRegistroModel[ ipvRegistroPK=" + nombre + " ]";
    }

}
