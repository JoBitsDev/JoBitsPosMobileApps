package com.services.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.utils.EnvironmentVariables;

import java.io.Serializable;
import java.util.Date;


/**
 * FirstDream
 *
 * @author Jorge
 */
@JsonIgnoreProperties
public class TransaccionModel implements Serializable {


    private static final long serialVersionUID = 1L;
    private Integer noTransaccion;
    private Date fecha;
    private Date hora;
    private Float cantidad;
    private String descripcion;
    private InsumoModel insumocodInsumo;

    public TransaccionModel() {
    }

    public TransaccionModel(Integer noTransaccion) {
        this.noTransaccion = noTransaccion;
    }

    public TransaccionModel(Integer noTransaccion, Date fecha, Date hora) {
        this.noTransaccion = noTransaccion;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Integer getNoTransaccion() {
        return noTransaccion;
    }

    public void setNoTransaccion(Integer noTransaccion) {
        this.noTransaccion = noTransaccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public InsumoModel getInsumocodInsumo() {
        return insumocodInsumo;
    }

    public void setInsumocodInsumo(InsumoModel insumocodInsumo) {
        this.insumocodInsumo = insumocodInsumo;
    }

    @Override
    public String toString() {
        return EnvironmentVariables.DATE_FORMAT.format(getFecha()) + " " +
                " " + EnvironmentVariables.HOUR_FORMAT.format(getHora()) +
                " " + getInsumocodInsumo().getNombre() +
                " " + getCantidad() + getDescripcion();
    }
}