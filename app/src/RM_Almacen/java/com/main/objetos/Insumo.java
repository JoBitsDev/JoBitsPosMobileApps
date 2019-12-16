package com.main.objetos;

public class Insumo {


    private String codInsumo;
    private String nombre;
    private String um;
    private Float costoPorUnidad;
    private Float stockEstimation;

    public Insumo() {
    }

    public Insumo(String codInsumo, String nombre, String um, Float costoPorUnidad,Float stockEstimation) {
        this.codInsumo = codInsumo;
        this.nombre = nombre;
        this.um = um;
        this.costoPorUnidad = costoPorUnidad;
        this.stockEstimation = stockEstimation;
    }

    public Insumo(String codInsumo) {
        this.codInsumo = codInsumo;
    }

    public Insumo(String codInsumo, String nombre) {
        this.codInsumo = codInsumo;
        this.nombre = nombre;
    }

    public String getCodInsumo() {
        return codInsumo;
    }

    public void setCodInsumo(String codInsumo) {
        this.codInsumo = codInsumo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public Float getCostoPorUnidad() {
        return costoPorUnidad;
    }

    public void setCostoPorUnidad(Float costoPorUnidad) {
        this.costoPorUnidad = costoPorUnidad;
    }

    public Float getStockEstimation() {
        return stockEstimation;
    }

    public void setStockEstimation(Float stockEstimation) {
        this.stockEstimation = stockEstimation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codInsumo != null ? codInsumo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Insumo)) {
            return false;
        }
        Insumo other = (Insumo) object;
        if ((this.codInsumo == null && other.codInsumo != null) || (this.codInsumo != null && !this.codInsumo.equals(other.codInsumo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre + "(" + codInsumo + ")";
    }
}
