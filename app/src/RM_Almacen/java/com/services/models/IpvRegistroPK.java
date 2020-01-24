/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.services.models;

import java.io.Serializable;
import java.util.Date;
/**
 * FirstDream
 * @author Jorge
 * 
 */
public class IpvRegistroPK implements Serializable {

    private String ipvinsumocodInsumo;
    private String ipvcocinacodCocina;
    private Date fecha;

    public IpvRegistroPK() {
    }

    public IpvRegistroPK(String ipvinsumocodInsumo, String ipvcocinacodCocina, Date fecha) {
        this.ipvinsumocodInsumo = ipvinsumocodInsumo;
        this.ipvcocinacodCocina = ipvcocinacodCocina;
        this.fecha = fecha;
    }

    public String getIpvinsumocodInsumo() {
        return ipvinsumocodInsumo;
    }

    public void setIpvinsumocodInsumo(String ipvinsumocodInsumo) {
        this.ipvinsumocodInsumo = ipvinsumocodInsumo;
    }

    public String getIpvcocinacodCocina() {
        return ipvcocinacodCocina;
    }

    public void setIpvcocinacodCocina(String ipvcocinacodCocina) {
        this.ipvcocinacodCocina = ipvcocinacodCocina;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ipvinsumocodInsumo != null ? ipvinsumocodInsumo.hashCode() : 0);
        hash += (ipvcocinacodCocina != null ? ipvcocinacodCocina.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpvRegistroPK)) {
            return false;
        }
        IpvRegistroPK other = (IpvRegistroPK) object;
        if ((this.ipvinsumocodInsumo == null && other.ipvinsumocodInsumo != null) || (this.ipvinsumocodInsumo != null && !this.ipvinsumocodInsumo.equals(other.ipvinsumocodInsumo))) {
            return false;
        }
        if ((this.ipvcocinacodCocina == null && other.ipvcocinacodCocina != null) || (this.ipvcocinacodCocina != null && !this.ipvcocinacodCocina.equals(other.ipvcocinacodCocina))) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.restmanager.IpvRegistroPK[ ipvinsumocodInsumo=" + ipvinsumocodInsumo + ", ipvcocinacodCocina=" + ipvcocinacodCocina + ", fecha=" + fecha + " ]";
    }

}
