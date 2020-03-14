package com.utils;

import com.services.models.UbicacionModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

/**
 * Clase: Utils
 * Clase que contiene las variables de ambiente predefinidas para la aplicacion, como ip, puerto, moneda y demas.
 */
public class Utils {

    /**
     * Redondea un valor a dos lugares despues de la coma.
     *
     * @param valorARedondear que se quiere redondear.
     * @return el String con el valor redondeado a dos lugares.
     */
    public static String setDosLugaresDecimales(float valorARedondear) {
        return Math.round(valorARedondear * Math.pow(10, 2)) / Math.pow(10, 2) + "";
    }

    public static String getSHA256(String text) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
            byte[] result = mDigest.digest(text.getBytes());
            return String.format("%1$064x", new java.math.BigInteger(1, result));
        } catch (NoSuchAlgorithmException ex) {
        }
        return null;
    }
}
