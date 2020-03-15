package com.services.models;

import java.io.Serializable;
import java.util.Date;

public class CacheModel implements Serializable {

    private String url;
    private String respuesta;
    private boolean volatil;
    private Date fecha;

    public CacheModel(String url, String respuesta, boolean volatil) {
        this.url = url;
        this.respuesta = respuesta;
        this.volatil = volatil;
        this.fecha = new Date();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public boolean isVolatil() {
        return volatil;
    }

    public void setVolatil(boolean volatil) {
        this.volatil = volatil;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
