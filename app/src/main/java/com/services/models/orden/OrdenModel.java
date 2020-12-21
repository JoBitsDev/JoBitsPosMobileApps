package com.services.models.orden;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Jorge
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdenModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codOrden;
    private boolean deLaCasa;
    private Float porciento;
    private Float gananciaXporciento;

    @JsonManagedReference
    private ArrayList<ProductoVentaOrdenModel> productoVentaOrdenList;

    private MesaModel mesacodMesaModel;

    public OrdenModel() {
    }

    public OrdenModel(String codOrden) {
        this.codOrden = codOrden;
    }

    public OrdenModel(String codOrden, boolean deLaCasa) {
        this.codOrden = codOrden;
        this.deLaCasa = deLaCasa;
    }

    public boolean isDeLaCasa() {
        return deLaCasa;
    }

    public ArrayList<ProductoVentaOrdenModel> getProductoVentaOrdenList() {
        return productoVentaOrdenList;
    }

    public void setProductoVentaOrdenList(ArrayList<ProductoVentaOrdenModel> productoVentaOrdenList) {
        this.productoVentaOrdenList = productoVentaOrdenList;
    }

    public String getCodOrden() {
        return codOrden;
    }

    public void setCodOrden(String codOrden) {
        this.codOrden = codOrden;
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

    @JsonGetter("mesacodMesa")
    public MesaModel getMesacodMesaModel() {
        return mesacodMesaModel;
    }

    @JsonSetter("mesacodMesa")
    public void setMesacodMesaModel(MesaModel mesacodMesaModel) {
        this.mesacodMesaModel = mesacodMesaModel;
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
