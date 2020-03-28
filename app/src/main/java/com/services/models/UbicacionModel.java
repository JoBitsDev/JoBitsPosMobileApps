package com.services.models;

import java.io.Serializable;

public class UbicacionModel implements Serializable {

    private final static String NOMBRE_XD = "Por defecto";
    private final static String IP_XD = "192.168.173.1";
    private final static String PUERTO_XD = "8080";
    private final static String USUARIO_XD = "Rest_Admin";
    private final static String PASS_XD = "admin";

    private String nombre;
    private String ip;
    private String puerto;
    private String usuario;
    private String password;


    public UbicacionModel(String nombre, String ip, String puerto, String usuario, String password) {
        this.nombre = nombre;
        this.ip = ip;
        this.puerto = puerto;
        this.usuario = usuario;
        this.password = password;
    }

    public static UbicacionModel getDefaultUbicacion() {
        return new UbicacionModel(NOMBRE_XD, IP_XD, PUERTO_XD, USUARIO_XD, PASS_XD);
    }

    public static UbicacionModel[] getDefaultArrayUbicaciones() {
        return new UbicacionModel[]{new UbicacionModel(NOMBRE_XD, IP_XD, PUERTO_XD, USUARIO_XD, PASS_XD)
                , new UbicacionModel(NOMBRE_XD, "192.168.173.2", PUERTO_XD, USUARIO_XD, PASS_XD)
                , new UbicacionModel(NOMBRE_XD, "192.168.173.3", PUERTO_XD, USUARIO_XD, PASS_XD)
                , new UbicacionModel(NOMBRE_XD, "192.168.173.4", PUERTO_XD, USUARIO_XD, PASS_XD)};
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
