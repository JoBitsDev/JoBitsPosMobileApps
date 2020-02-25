package com.utils;

import com.services.models.UbicacionModel;

import java.text.SimpleDateFormat;

/**
 * Clase: Utils
 * Clase que contiene las variables de ambiente predefinidas para la aplicacion, como ip, puerto, moneda y demas.
 */
public class EnvironmentVariables {

    /**
     * TODO: Ni idea de que es esto.
     */
    public static final String
            ESTADO_MESA_VACIA = "vacia",
            ESTADO_MESA_ESPERANDO_CONFIRMACION = "esperando confirmacion";

    /**
     * Puerto para que la notificacion se conecte con el servidor.
     */
    public static final int SOCKET_PORT = 8888;
    public static final String CONFIG_PATH = "config.json";
    public static String NOMBRE_REST = "";

    private static UbicacionModel ubicacionActual = UbicacionModel.getDefaultUbicacion();

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");
    public static SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("hh:mm a");

    /**
     * Path de inicio de las peticiones.
     */
    public static String STARTPATH = "jobits/pos/";

    /**
     * Monedo principal a trabajar.
     */
    public static String MONEDA_PRINCIPAL = " CUC";

    /**
     * Monedo secundarios a trabajar.
     */
    public static String MONEDA_SECUNDARIA = " MN";

    /**
     * Tasa de conversión de moneda principal a secundaria.
     */
    public static int CAMBIO = 24;

    /**
     *
     */
    public static int MAYOR = 3;

    /**
     *
     */
    public static int MINOR = 3;

    /**
     * Redondea un valor a dos lugares despues de la coma.
     *
     * @param valorARedondear que se quiere redondear.
     * @return el String con el valor redondeado a dos lugares.
     */
    public static String setDosLugaresDecimales(float valorARedondear) {
        return Math.round(valorARedondear * Math.pow(10, 2)) / Math.pow(10, 2) + "";
    }

    public static String getIP() {
        return ubicacionActual.getIp();
    }

    public static String getPORT() {
        return ubicacionActual.getPuerto();
    }

    public static String getNombre() {
        return ubicacionActual.getNombre();
    }

    public static void setUbicacionActual(UbicacionModel act) {
        ubicacionActual = act;
    }
}
