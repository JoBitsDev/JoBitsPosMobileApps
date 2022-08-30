package com.services.web_connections.interfaces;

import com.services.models.Token;

import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface LoginWCI {

    @GET("pos/auth/basic")
    Call<Map<String,Object>> getToken(@Header("Tennant") String tennantToken, @Header("Authorization") String base64Credentials);

    @GET("tennant/cuenta/{idCuenta}/token-for/{idBaseDatos}")
    Call<Token> getTennantToken(@Header("Authorization") String base64CredentialsForTennant,
                                 @Path("idCuenta") int idCuenta, @Path("idBaseDatos") int idBaseDatos);
}
