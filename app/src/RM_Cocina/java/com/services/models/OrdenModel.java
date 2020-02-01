package com.services.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Jorge
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdenModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codOrden;
    private Integer hora;
    private boolean deLaCasa;
    private Float porciento;
    private Float gananciaXporciento;
    private Collection<ProductoVentaOrdenModel> productoVentaOrdenList;

    private MesaModel mesacodMesa;

    public OrdenModel() {
    }

    public OrdenModel(String codOrden) {
        this.codOrden = codOrden;
    }

    public OrdenModel(String codOrden, boolean deLaCasa) {
        this.codOrden = codOrden;
        this.deLaCasa = deLaCasa;
    }

    public String getCodOrden() {
        return codOrden;
    }

    public void setCodOrden(String codOrden) {
        this.codOrden = codOrden;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public boolean getDeLaCasa() {
        return deLaCasa;
    }

    public void setDeLaCasa(boolean deLaCasa) {
        this.deLaCasa = deLaCasa;
    }

    public Float getPorciento() {
        return porciento;
    }

    public void setPorciento(Float porciento) {
        this.porciento = porciento;
    }

    public Float getGananciaXporciento() {
        return gananciaXporciento;
    }

    public void setGananciaXporciento(Float gananciaXporciento) {
        this.gananciaXporciento = gananciaXporciento;
    }


    public void setProductoVentaOrdenList(Collection<ProductoVentaOrdenModel> productoVentaOrdenList) {
        this.productoVentaOrdenList = productoVentaOrdenList;
    }



    public MesaModel getMesacodMesa() {
        return mesacodMesa;
    }

    public void setMesacodMesa(MesaModel mesacodMesa) {
        this.mesacodMesa = mesacodMesa;
    }


    
    public void addProducto(String codProducto){
        ArrayList<ProductoVentaModel> p = new ArrayList(productoVentaOrdenList);
        //TODO: falta esto por hacer
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codOrden != null ? codOrden.hashCode() : 0);
        return hash;
    }
    
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenModel)) {
            return false;
        }
        OrdenModel other = (OrdenModel) object;
        if ((this.codOrden == null && other.codOrden != null) || (this.codOrden != null && !this.codOrden.equals(other.codOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.restManager.OrdenActivity[ codOrden=" + codOrden + " ]";
    }
    
}
