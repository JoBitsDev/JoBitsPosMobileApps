package com.services.web_connections;

public class ResumenVentasWCS extends SimpleWebConnectionService {

    private final String resumen_URL;

    public ResumenVentasWCS() {
        resumen_URL = path + "venta/";
    }

    public String getResumenVentas(String fecha) throws Exception {
        return connect(resumen_URL + "?fecha=" + fecha, null, TOKEN, HTTPMethod.GET);
    }

}
