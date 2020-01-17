package com.services.web_connections;

public class ResumenVentasWCS extends SimpleWebConnectionService {

    private final String resumen_URL;

    public ResumenVentasWCS() {
        resumen_URL = path + "com.jobits.pos.venta/SALES";
    }

    public String getResumenVentas(String fecha) throws Exception {
        return connectPost(resumen_URL, fecha, TOKEN);
    }

}
