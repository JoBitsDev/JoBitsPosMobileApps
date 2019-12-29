package com.utils;

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

    /**
     * Ip del servidor.
     */
    public static String IP = "10.0.2.2";

            //"192.168.173.1";

    /**
     * Puerto para la coneccion.
     */
    public static String PORT = "8080";

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
     * Método para cambiar el IP de la coneccion.
     *
     * @param ip para el que se va a cambiar.
     */
    public static void changeIP(String ip) {
        IP = ip;
    }

    /**
     * Redondea un valor a dos lugares despues de la coma.
     *
     * @param valorARedondear que se quiere redondear.
     * @return el String con el valor redondeado a dos lugares.
     */
    public static String setDosLugaresDecimales(float valorARedondear) {
        return Math.round(valorARedondear * Math.pow(10, 2)) / Math.pow(10, 2) + MONEDA_PRINCIPAL;
    }

}
