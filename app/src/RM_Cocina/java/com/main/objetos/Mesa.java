/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import java.io.Serializable;
import javax.xml.parsers.DocumentBuilder;


/**
 *
 * @author Jorge
 */
public class Mesa implements Comparable<Mesa>{

    private static final long serialVersionUID = 1L;

    private String codMesa;

    private String estado;
    private Integer capacidadMax;
    private Double ubicacion;
    private Boolean estallena;



    public Mesa() {
    }

    public Mesa(String codMesa) {
        this.codMesa = codMesa;

    }

    public Mesa(String codMesa, String estado) {
        this.codMesa = codMesa;
        this.estado = estado;
    }

    public Mesa(String codMesa, String estado, Integer capacidadMax) {
        this.codMesa = codMesa;
        this.estado = estado;
        this.capacidadMax = capacidadMax;
    }

    public String getCodMesa() {
        return codMesa;
    }

    public void setCodMesa(String codMesa) {
        this.codMesa = codMesa;
    }

    public String getEstado() {
        return estado;
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getCapacidadMax() {
        return capacidadMax;
    }

    public void setCapacidadMax(Integer capacidadMax) {
        this.capacidadMax = capacidadMax;
    }

    public Double getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Double ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Boolean getEstallena() {
        return estallena;
    }

    public void setEstallena(Boolean estallena) {
        this.estallena = estallena;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codMesa != null ? codMesa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Mesa)) {
            return false;
        }
        Mesa other = (Mesa) object;
        if ((this.codMesa == null && other.codMesa != null) || (this.codMesa != null &&
                    !this.codMesa.equals(other.codMesa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[codMesa=" + codMesa + " ]";
    }

    @Override
    public int compareTo(Mesa another) {
        return this.codMesa.compareTo(another.codMesa);
    }
}
