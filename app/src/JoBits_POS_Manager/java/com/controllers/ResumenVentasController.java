package com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.models.VentaResumenModel;
import com.services.web_connections.ResumenVentasWCS;

public class ResumenVentasController extends BaseController {
    private ResumenVentasWCS resumenVentasWCS = new ResumenVentasWCS();

    public VentaResumenModel getResumenVentas(String fecha) throws Exception {
        String body = resumenVentasWCS.getResumenVentas(fecha);
        return new ObjectMapper().readValue(body, VentaResumenModel.class);
    }
}
