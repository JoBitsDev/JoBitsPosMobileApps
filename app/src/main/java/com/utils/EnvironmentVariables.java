package com.utils;

/**
 * Created by Jorge on 3/12/17.
 */

public class EnvironmentVariables {
    public static final String
            ESTADO_MESA_VACIA = "vacia",
            ESTADO_MESA_ESPERANDO_CONFIRMACION = "esperando confirmacion";

    public static String IP = "192.168.173.1";

    public static String MONEDA_PRINCIPAL = " MN";

    public static String MONEDA_SECUNDARIA = " CUC";

    public static String PORT = "8080";

    public static int conversion = 24;

    public static String STARTPATH = "RM/rest/";

    public static void changeIP(String ip) {
        IP = ip;
    }

    public static String setDosLugaresDecimales(float valorARedondear) {
        return Math.round(valorARedondear * Math.pow(10, 2)) / Math.pow(10, 2) + MONEDA_PRINCIPAL;
    }

}
