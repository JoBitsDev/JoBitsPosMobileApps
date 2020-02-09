package com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.models.DetallesVentasModel;
import com.services.models.VentaResumenModel;
import com.services.web_connections.HTTPMethod;
import com.services.web_connections.ResumenVentasWCS;

import java.util.List;

public class ResumenVentasController extends BaseController {
    private ResumenVentasWCS resumenVentasWCS = new ResumenVentasWCS();

    public VentaResumenModel getResumenVentas(String fecha) throws Exception {
        String body = resumenVentasWCS.getResumenVentas(fecha);
        return new ObjectMapper().readValue(body, VentaResumenModel.class);
    }

    public List<DetallesVentasModel> getDetallesPorArea(String fecha, String areaCod) throws Exception {
        return resumenVentasWCS.getDetallesPorArea(fecha, areaCod);
    }

    public List<DetallesVentasModel> getDetallesPorDependientes(String fecha, String usuario) throws Exception {
        return resumenVentasWCS.getDetallesPorDependientes(fecha, usuario);
    }

    public List<DetallesVentasModel> getDetallesPorCocina(String fecha, String cocinaCod) throws Exception {
        return resumenVentasWCS.getDetallesPorCocina(fecha, cocinaCod);
    }

    public List<DetallesVentasModel> getDetallesPor(String fecha) throws Exception {
        return resumenVentasWCS.getDetallesPor(fecha);
    }
}
