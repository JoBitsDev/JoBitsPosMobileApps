package com.services.models.orden;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Nota {

    private String descripcion = "Sin Especificaciones";

    public Nota() {
    }

    public Nota(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return this.descripcion;

    }

    public void setDescripcion(String descripcion) {
        String notaAntigua = getDescripcion();
        if (descripcion == null || descripcion.equals("")) {
            this.descripcion = (notaAntigua);
        } else {
            this.descripcion = (descripcion);
        }
    }

}

