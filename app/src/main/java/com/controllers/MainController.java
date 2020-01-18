package com.controllers;

import com.services.models.UbicacionModel;
import com.utils.EnvironmentVariables;

/**
 * Capa: Controllers
 * Clase controladora de MainActivity, encargada de manejar sus peticiones con la capa inferior.
 *
 * @extends BaseController ya que es un controller plicacion.
 */
public class MainController extends BaseController {

    private UbicacionModel[] ubicaciones = new UbicacionModel[]{new UbicacionModel("A", "192.168.173.1", "8080"), new UbicacionModel("B", "192.168.173.2", "8080")};

    public String[] getAllUbicaciones() {
        String[] arr = new String[ubicaciones.length];
        for (int i = 0; i < ubicaciones.length; i++) {
            arr[i] = ubicaciones[i].getNombre() + ": " + ubicaciones[i].getIp();
        }
        return arr;
    }

    public void agregarUbicacion(String s) {

    }

    public void changeUbication(int wich) {
        EnvironmentVariables.setUbicacionActual(ubicaciones[wich]);
    }
}
