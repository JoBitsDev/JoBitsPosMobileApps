/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services.models;

import java.util.List;

/**
 * Clase para enviar la informacion en JSON y parsearla en el dispositivo
 * destino
 *
 * @author Jorge
 */
public class VentaResumenModel {

    private float ventaTotal,
            ventaNeta,
            gastosInsumo,
            gastosSalario,
            autorizos,
            gastosOtros;

    private List<AreaListModel> areas;
    private List<DpteListModel> dptes;
    private List<PuntoElaboracionListModel> ptosElaboracion;

    public VentaResumenModel() {
    }

    public float getVentaTotal() {
        return ventaTotal;
    }

    public float getVentaNeta() {
        return ventaNeta;
    }

    public float getGastosInsumo() {
        return gastosInsumo;
    }

    public float getGastosSalario() {
        return gastosSalario;
    }

    public float getAutorizos() {
        return autorizos;
    }

    public float getGastosOtros() {
        return gastosOtros;
    }

    public List<AreaListModel> getAreas() {
        return areas;
    }

    public List<DpteListModel> getDptes() {
        return dptes;
    }

    public List<PuntoElaboracionListModel> getPtosElaboracion() {
        return ptosElaboracion;
    }

    public void setVentaTotal(float ventaTotal) {
        this.ventaTotal = ventaTotal;
    }

    public void setVentaNeta(float ventaNeta) {
        this.ventaNeta = ventaNeta;
    }

    public void setGastosInsumo(float gastosInsumo) {
        this.gastosInsumo = gastosInsumo;
    }

    public void setGastosSalario(float gastosSalario) {
        this.gastosSalario = gastosSalario;
    }

    public void setAutorizos(float autorizos) {
        this.autorizos = autorizos;
    }

    public void setGastosOtros(float gastosOtros) {
        this.gastosOtros = gastosOtros;
    }

    public void setAreas(List<AreaListModel> areas) {
        this.areas = areas;
    }

    public void setDptes(List<DpteListModel> dptes) {
        this.dptes = dptes;
    }

    public void setPtosElaboracion(List<PuntoElaboracionListModel> ptosElaboracion) {
        this.ptosElaboracion = ptosElaboracion;
    }
}
