package com.main.objetos;

public class InsumoAlmacen implements Comparable<InsumoAlmacen>{


    protected InsumoAlmacenPK insumoAlmacenPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Float cantidad;
    private Float valorMonetario;
    private Almacen almacen;
    private Insumo insumo;

    public InsumoAlmacen(InsumoAlmacenPK insumoAlmacenPK, Float cantidad, Float valorMonetario, Almacen almacen, Insumo insumo) {
        this.insumoAlmacenPK = insumoAlmacenPK;
        this.cantidad = cantidad;
        this.valorMonetario = valorMonetario;
        this.almacen = almacen;
        this.insumo = insumo;
    }

    public InsumoAlmacen(InsumoAlmacenPK insumoAlmacenPK, Float cantidad, Float valorMonetario, Insumo insumo) {
        this.insumoAlmacenPK = insumoAlmacenPK;
        this.cantidad = cantidad;
        this.valorMonetario = valorMonetario;
        this.insumo = insumo;
    }

    public InsumoAlmacen(InsumoAlmacenPK insumoAlmacenPK) {
        this.insumoAlmacenPK = insumoAlmacenPK;
    }

    public InsumoAlmacen(String insumocodInsumo, String almacencodAlmacen) {
        this.insumoAlmacenPK = new InsumoAlmacenPK(insumocodInsumo, almacencodAlmacen);
    }

    public InsumoAlmacenPK getInsumoAlmacenPK() {
        return insumoAlmacenPK;
    }

    public void setInsumoAlmacenPK(InsumoAlmacenPK insumoAlmacenPK) {
        this.insumoAlmacenPK = insumoAlmacenPK;
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

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (insumoAlmacenPK != null ? insumoAlmacenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InsumoAlmacen)) {
            return false;
        }
        InsumoAlmacen other = (InsumoAlmacen) object;
        if ((this.insumoAlmacenPK == null && other.insumoAlmacenPK != null) || (this.insumoAlmacenPK != null && !this.insumoAlmacenPK.equals(other.insumoAlmacenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "(" +almacen +") " + insumo;
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
    public int compareTo(InsumoAlmacen another) {
        return this.getInsumo().getNombre().compareTo(another.getInsumo().getNombre());
    }
}
