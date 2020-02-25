package com.controllers;

import com.services.models.ConfigModel;
import com.services.models.UbicacionModel;
import com.services.web_connections.GeneralWCS;
import com.utils.EnvironmentVariables;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

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

    public void readInfo() throws Exception {
        HashMap<String, Object> hm = new GeneralWCS().readInfo();
        EnvironmentVariables.NOMBRE_REST = (String) hm.get("nombre");
        EnvironmentVariables.MONEDA_PRINCIPAL = (String) hm.get("monedaPrincipal");
        EnvironmentVariables.MONEDA_SECUNDARIA = (String) hm.get("monedaSecundaria");
        EnvironmentVariables.MAYOR = (Integer) hm.get("majorVersion");
        EnvironmentVariables.MINOR = (Integer) hm.get("minorVersion");
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

    public void editarUbicacion(int pos, UbicacionModel ub) {
        cfg.getUbicaciones()[pos] = ub;
        setSelected(pos);
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
