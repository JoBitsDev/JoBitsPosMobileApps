package com.utils;

import com.services.models.UbicacionModel;

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

    private static UbicacionModel ubicacionActual = UbicacionModel.getDefaultUbicacion();

    /**
     * Path de inicio de las peticiones.
     */
    public static String STARTPATH = "RM/rest/";

    /**
     * Valor devuelto por el servidor cuando la peticion devuelve true.
     */
    public static String PETITION_TRUE = "1";

    /**
     * Valor devuelto por el servidor cuando la peticion devuelve false.
     */
    public static String PETITION_FALSE = "0";

    /**
     * Valor devuelto por el servidor cuando la peticion devuelve un error.
     */
    public static String PETITION_ERROR = "-1";

    /**
     * Monedo principal a trabajar.
     */
    public static String MONEDA_PRINCIPAL = " MN";

    /**
     * Monedo secundarios a trabajar.
     */
    public static String MONEDA_SECUNDARIA = " CUC";

    /**
     * Tasa de conversión de moneda principal a secundaria.
     */
    public static int conversion = 24;

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
