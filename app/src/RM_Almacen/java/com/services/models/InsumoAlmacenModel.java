package com.services.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class InsumoAlmacenModel {


    protected InsumoAlmacenPKModel insumoAlmacenPKModel;

    private Float cantidad;
    private Float valorMonetario;
    private InsumoModel insumoModel;

    public InsumoAlmacenModel(InsumoAlmacenPKModel insumoAlmacenPKModel, Float cantidad, Float valorMonetario, AlmacenModel almacenModel, InsumoModel insumoModel) {
        this.insumoAlmacenPKModel = insumoAlmacenPKModel;
        this.cantidad = cantidad;
        this.valorMonetario = valorMonetario;
        this.insumoModel = insumoModel;
    }

    public InsumoAlmacenModel(InsumoAlmacenPKModel insumoAlmacenPKModel, Float cantidad, Float valorMonetario, InsumoModel insumoModel) {
        this.insumoAlmacenPKModel = insumoAlmacenPKModel;
        this.cantidad = cantidad;
        this.valorMonetario = valorMonetario;
        this.insumoModel = insumoModel;
    }

    public InsumoAlmacenModel() {
    }

    @JsonGetter("insumoAlmacenPK")
    public InsumoAlmacenPKModel getInsumoAlmacenPKModel() {
        return insumoAlmacenPKModel;
    }

    @JsonSetter("insumoAlmacenPK")
    public void setInsumoAlmacenPKModel(InsumoAlmacenPKModel insumoAlmacenPKModel) {
        this.insumoAlmacenPKModel = insumoAlmacenPKModel;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public Float getValorMonetario() {
        return valorMonetario;
    }

    public void setValorMonetario(Float valorMonetario) {
        this.valorMonetario = valorMonetario;
    }

    public String getCodAlmacen() {
        return insumoAlmacenPKModel.getAlmacencodAlmacen();
    }

    @JsonGetter("insumo")
    public InsumoModel getInsumoModel() {
        return insumoModel;
    }

    @JsonSetter("insumo")
    public void setInsumoModel(InsumoModel insumoModel) {
        this.insumoModel = insumoModel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (insumoAlmacenPKModel != null ? insumoAlmacenPKModel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InsumoAlmacenModel)) {
            return false;
        }
        InsumoAlmacenModel other = (InsumoAlmacenModel) object;
        if ((this.insumoAlmacenPKModel == null && other.insumoAlmacenPKModel != null) || (this.insumoAlmacenPKModel != null && !this.insumoAlmacenPKModel.equals(other.insumoAlmacenPKModel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" + getCodAlmacen() + ") " + insumoModel;
    }


}
