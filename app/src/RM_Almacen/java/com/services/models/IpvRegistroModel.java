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

public class IpvRegistroModel implements Serializable {

    private static final long serialVersionUID = 1L;

    protected IpvRegistroPK ipvRegistroPK;
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

    public IpvRegistroModel(IpvRegistroPK ipvRegistroPK) {
        this.ipvRegistroPK = ipvRegistroPK;
    }

    public IpvRegistroModel(String ipvinsumocodInsumo, String ipvcocinacodCocina, Date fecha) {
        this.ipvRegistroPK = new IpvRegistroPK(ipvinsumocodInsumo, ipvcocinacodCocina, fecha);
    }

    public IpvRegistroPK getIpvRegistroPK() {
        return ipvRegistroPK;
    }

    public void setIpvRegistroPK(IpvRegistroPK ipvRegistroPK) {
        this.ipvRegistroPK = ipvRegistroPK;
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
        hash += (ipvRegistroPK != null ? ipvRegistroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IpvRegistroModel)) {
            return false;
        }
        IpvRegistroModel other = (IpvRegistroModel) object;
        if ((this.ipvRegistroPK == null && other.ipvRegistroPK != null) || (this.ipvRegistroPK != null && !this.ipvRegistroPK.equals(other.ipvRegistroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.restmanager.IpvRegistroModel[ ipvRegistroPK=" + ipvRegistroPK + " ]";
    }

}
