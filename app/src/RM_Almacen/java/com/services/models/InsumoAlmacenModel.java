package com.services.models;

public class InsumoAlmacenModel implements Comparable<InsumoAlmacenModel>{


    protected InsumoAlmacenPKModel insumoAlmacenPKModel;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
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

    public InsumoAlmacenModel(InsumoAlmacenPKModel insumoAlmacenPKModel) {
        this.insumoAlmacenPKModel = insumoAlmacenPKModel;
    }

    public InsumoAlmacenModel(String insumocodInsumo, String almacencodAlmacen) {
        this.insumoAlmacenPKModel = new InsumoAlmacenPKModel(insumocodInsumo, almacencodAlmacen);
    }

    public InsumoAlmacenPKModel getInsumoAlmacenPKModel() {
        return insumoAlmacenPKModel;
    }

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

    public InsumoModel getInsumoModel() {
        return insumoModel;
    }

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
        return "(" + getCodAlmacen() +") " + insumoModel;
    }

    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(InsumoAlmacenModel another) {
        return this.getInsumoModel().getNombre().compareTo(another.getInsumoModel().getNombre());
    }
}
