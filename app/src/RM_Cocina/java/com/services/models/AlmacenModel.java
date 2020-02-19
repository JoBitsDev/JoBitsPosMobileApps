package com.services.models;

import java.util.List;

public class AlmacenModel {


    private static final long serialVersionUID = 1L;
    private String codAlmacen;
    private String nombre;
    private Integer cantidadInsumos;
    private Float valorMonetario;
    private List<InsumoAlmacenModel> insumoAlmacenModelList;

    public AlmacenModel() {
    }

    public AlmacenModel(String codAlmacen) {
        this.codAlmacen = codAlmacen;
    }

    public String getCodAlmacen() {
        return codAlmacen;
    }

    public void setCodAlmacen(String codAlmacen) {
        this.codAlmacen = codAlmacen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidadInsumos() {
        return cantidadInsumos;
    }

    public void setCantidadInsumos(Integer cantidadInsumos) {
        this.cantidadInsumos = cantidadInsumos;
    }

    public Float getValorMonetario() {
        return valorMonetario;
    }

    public void setValorMonetario(Float valorMonetario) {
        this.valorMonetario = valorMonetario;
    }

    public List<InsumoAlmacenModel> getInsumoAlmacenModelList() {
        return insumoAlmacenModelList;
    }

    public void setInsumoAlmacenModelList(List<InsumoAlmacenModel> insumoAlmacenModelList) {
        this.insumoAlmacenModelList = insumoAlmacenModelList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codAlmacen != null ? codAlmacen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlmacenModel)) {
            return false;
        }
        AlmacenModel other = (AlmacenModel) object;
        if ((this.codAlmacen == null && other.codAlmacen != null) || (this.codAlmacen != null && !this.codAlmacen.equals(other.codAlmacen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codAlmacen +" : "+nombre ;
    }
}
