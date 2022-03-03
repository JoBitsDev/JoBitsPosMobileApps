package com.services.web_connections.interfaces;


import com.services.models.IpvRegistroModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AlmacenWCI {

    public static final String IPV_PATH = "ipv/";
    public static final String GET_IPV_REGISTRO_LIST_PATH = IPV_PATH + "/ipv-registro-list/{cod_cocina}";
    public static final String GET_IPV_REGISTRO_VENTA_LIST_PATH = IPV_PATH + "/ipv-venta-list/{cod_cocina}";


    @GET(GET_IPV_REGISTRO_LIST_PATH)
    Call<List<IpvRegistroModel>> getIpvRegistro(
            @Header("Tennant") String tennantToken,
            @Header("Authorization") String bearerToken,
            @Path("cod_cocina") String codCocina);

    @GET(GET_IPV_REGISTRO_VENTA_LIST_PATH)
    Call<List<IpvRegistroModel>> getIpvventa(
            @Header("Tennant") String tennantToken,
            @Header("Authorization") String bearerToken,
            @Path("cod_cocina") String codCocina);

}
