
package com.main.objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
/**
 *
 * @author Jorge
 */

public class Orden implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codOrden;
    private Integer hora;
    private boolean deLaCasa;
    private Float porciento;
    private Float gananciaXporciento;
    private Collection<ProductovOrden> productovOrdenCollection;

    private Mesa mesacodMesa;

    public Orden() {
    }

    public Orden(String codOrden) {
        this.codOrden = codOrden;
    }

    public Orden(String codOrden, boolean deLaCasa) {
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


    public void setProductovOrdenCollection(Collection<ProductovOrden> productovOrdenCollection) {
        this.productovOrdenCollection = productovOrdenCollection;
    }



    public Mesa getMesacodMesa() {
        return mesacodMesa;
    }

    public void setMesacodMesa(Mesa mesacodMesa) {
        this.mesacodMesa = mesacodMesa;
    }


    
    public void addProducto(String codProducto){
        ArrayList<ProductoVenta> p = new ArrayList(productovOrdenCollection);
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
        if (!(object instanceof Orden)) {
            return false;
        }
        Orden other = (Orden) object;
        if ((this.codOrden == null && other.codOrden != null) || (this.codOrden != null && !this.codOrden.equals(other.codOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.restManager.Orden[ codOrden=" + codOrden + " ]";
    }
    
}
