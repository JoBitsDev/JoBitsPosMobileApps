package com.services.web_connections;



import com.utils.EnvironmentVariables;

import java.util.concurrent.ExecutionException;

import static com.utils.EnvironmentVariables.IP;

/**
 * Created by Jorge on 2/8/18.
 */

public class CartaWebConnection extends SimpleWebConnectionService {


    private String urlNombreRest = "http://"+ EnvironmentVariables.IP +":8080/RM/rest/com.restmanager.carta/NOMBRE_REST";

    public CartaWebConnection() {
        super();
    }

    public String getNombreRest(){
        try {
            return connect(urlNombreRest);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }


}
