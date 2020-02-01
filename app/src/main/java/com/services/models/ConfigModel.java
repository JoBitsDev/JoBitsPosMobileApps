package com.services.models;

import java.io.Serializable;

public class ConfigModel implements Serializable {

    private int selected = 0;
    private UbicacionModel[] ubicaciones = UbicacionModel.getDefaultArrayUbicaciones();

    public ConfigModel() {
    }

    public ConfigModel(int selected, UbicacionModel[] ubicaciones) {
        this.selected = selected;
        this.ubicaciones = ubicaciones;
    }

    public UbicacionModel getSelectedUbicacion() {
        return ubicaciones[selected];
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public UbicacionModel[] getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(UbicacionModel[] ubicaciones) {
        this.ubicaciones = ubicaciones;
    }
}
