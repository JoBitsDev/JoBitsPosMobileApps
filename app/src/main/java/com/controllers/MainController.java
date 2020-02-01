package com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.models.ConfigModel;
import com.services.models.UbicacionModel;
import com.utils.EnvironmentVariables;
import com.utils.exception.ExceptionHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Capa: Controllers
 * Clase controladora de MainActivity, encargada de manejar sus peticiones con la capa inferior.
 *
 * @extends BaseController ya que es un controller plicacion.
 */
public class MainController extends BaseController {

    private ConfigModel cfg;

    public ConfigModel getCfg() {
        return cfg;
    }

    public void setCfg(ConfigModel cfg) {
        this.cfg = cfg;
        cambiarUbicacion();
    }

    public void setSelected(int wich) {
        cfg.setSelected(wich);
        cambiarUbicacion();
    }

    public void guardarCFG(FileOutputStream fileOutputStream) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(cfg);
            fileOutputStream.close();
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void cambiarUbicacion() {
        EnvironmentVariables.setUbicacionActual(cfg.getSelectedUbicacion());
    }

    public String[] getAllUbicaciones() {
        String resp[] = new String[cfg.getUbicaciones().length];
        for (int i = 0; i < resp.length; i++) {
            resp[i] = cfg.getUbicaciones()[i].getNombre() + ": " + cfg.getUbicaciones()[i].getIp();
        }
        return resp;
    }
}
