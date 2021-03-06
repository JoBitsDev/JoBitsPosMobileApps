package com.services.models;

public class InsumoAlmacenPKModel {


    private String insumocodInsumo;
    private String almacencodAlmacen;

    public InsumoAlmacenPKModel() {
    }

    public InsumoAlmacenPKModel(String insumocodInsumo, String almacencodAlmacen) {
        this.insumocodInsumo = insumocodInsumo;
        this.almacencodAlmacen = almacencodAlmacen;
    }

    public String getInsumocodInsumo() {
        return insumocodInsumo;
    }

    public void setInsumocodInsumo(String insumocodInsumo) {
        this.insumocodInsumo = insumocodInsumo;
    }

    public String getAlmacencodAlmacen() {
        return almacencodAlmacen;
    }

    public void setAlmacencodAlmacen(String almacencodAlmacen) {
        this.almacencodAlmacen = almacencodAlmacen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (insumocodInsumo != null ? insumocodInsumo.hashCode() : 0);
        hash += (almacencodAlmacen != null ? almacencodAlmacen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InsumoAlmacenPKModel)) {
            return false;
        }
        InsumoAlmacenPKModel other = (InsumoAlmacenPKModel) object;
        if ((this.insumocodInsumo == null && other.insumocodInsumo != null) || (this.insumocodInsumo != null && !this.insumocodInsumo.equals(other.insumocodInsumo))) {
            return false;
        }
        if ((this.almacencodAlmacen == null && other.almacencodAlmacen != null) || (this.almacencodAlmacen != null && !this.almacencodAlmacen.equals(other.almacencodAlmacen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restManager.persistencia.InsumoAlmacenPKModel[ insumocodInsumo=" + insumocodInsumo + ", almacencodAlmacen=" + almacencodAlmacen + " ]";
    }
}
