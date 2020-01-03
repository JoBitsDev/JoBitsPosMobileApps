/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.models;


import android.graphics.Color;

import com.utils.EnvironmentVariables;

/**
 * @author Jorge
 */
public class MesaModel implements Comparable<MesaModel> {

    private static final long serialVersionUID = 1L;

    private String codMesa;

    private String estado;
    private Integer capacidadMax;
    private Double ubicacion;
    private Boolean estallena;


    public MesaModel() {
    }

    public MesaModel(String codMesa) {
        this.codMesa = codMesa;

    }

    public MesaModel(String codMesa, String estado) {
        this.codMesa = codMesa;
        this.estado = estado;
    }

    public MesaModel(String codMesa, String estado, Integer capacidadMax) {
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
        if (!(object instanceof MesaModel)) {
            return false;
        }
        MesaModel other = (MesaModel) object;
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
    public int compareTo(MesaModel another) {
        int o1 = Integer.parseInt(getCodMesa().split("-")[1]);
        int o2 = Integer.parseInt(another.getCodMesa().split("-")[1]);
        return o1 - o2;
        //los primero if son para verificar si las mesas tienen orden
        /*if (getEstado().compareToIgnoreCase(EnvironmentVariables.ESTADO_MESA_VACIA) == 0) {
            return -1;//si esta no tiene orden va a ser < que la otra
        } else if (another.getEstado().compareToIgnoreCase(EnvironmentVariables.ESTADO_MESA_VACIA) == 0) {
            return 1;//si esta no tiene orden va a ser > que la otra
        } else if (getEstado().equals(EnvironmentVariables.ESTADO_MESA_ESPERANDO_CONFIRMACION)) {
            return -1;//si esta no tiene orden va a ser < que la otra
        } else if (another.getEstado().equals(EnvironmentVariables.ESTADO_MESA_ESPERANDO_CONFIRMACION)) {
            return 1;//si esta no tiene orden va a ser > que la otra
        } else {//si las dos tienen orden ver las ordenes

            int orden1 = Integer.parseInt(this.getEstado().split(" ")[0].split("-")[1]);
            int orden2 = Integer.parseInt(another.getEstado().split(" ")[0].split("-")[1]);
            int compPorOrden = orden1 - orden2;

            if (compPorOrden == 0) {//tienen la misma orden, compara por mesas, imposible que pase pero para si por si acaso
                int o1 = Integer.parseInt(getCodMesa().split("-")[1]);
                int o2 = Integer.parseInt(another.getCodMesa().split("-")[1]);
                return o1 - o2;
            } else {
                return compPorOrden;
            }

        }*/
    }
}
