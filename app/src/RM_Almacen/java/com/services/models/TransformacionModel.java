package com.services.models;

import java.util.List;

public class TransformacionModel {
    private List<InsumoAlmacenModel> entradas;
    private List<InsumoAlmacenModel> salidas;

    public TransformacionModel(List<InsumoAlmacenModel> entradas, List<InsumoAlmacenModel> salidas) {
        this.entradas = entradas;
        this.salidas = salidas;
    }

    public List<InsumoAlmacenModel> getEntradas() {
        return entradas;
    }

    public void setEntradas(List<InsumoAlmacenModel> entradas) {
        this.entradas = entradas;
    }

    public List<InsumoAlmacenModel> getSalidas() {
        return salidas;
    }

    public void setSalidas(List<InsumoAlmacenModel> salidas) {
        this.salidas = salidas;
    }
}
