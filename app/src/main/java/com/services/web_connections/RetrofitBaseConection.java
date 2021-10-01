package com.services.web_connections;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.utils.EnvironmentVariables;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitBaseConection {

    protected static Retrofit retrofit;

    protected static final ObjectMapper oMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);


    /**
     * Tiempo maximo esperado para la lectura.
     */
    public static final int MAX_READ_TIME = 5 * 1000;
    /**
     * Tiempo maximo esperado para la respuesta del servidor.
     */
    public static final int MAX_RESPONSE_TIME = 3 * 1000;
    public static final String DIR_CACHE = EnvironmentVariables.PERSISTENCE_PATH + "/";
    /**
     * Token para las llamandas seguras al servidor.
     */
    protected static String TOKEN = null;
    /**
     * Token para autenticarse en el servidor y que este lo redirija hacia la base de datos correspondiente
     */
    protected static String TENNANT_TOKEN = null;
    protected final String HTTP_HEADER_LOCATION = "Location";
    protected final String HTTP_HEADER_TENNANT_ID = "TennantId ";
    protected final String HTTP_HEADER_AUTHORITATION = "Authorization";
    protected final String HTTP_HEADER_BEARER = "Bearer ";
    protected final String HTTP_HEADER_BASIC = "Basic ";

    /**
     * Partes de la URL de las consultas.
     */
    protected String
            ip,
            port,
            path;


    public static void setRetrofit(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(oMapper))
                .build();
    }
}
