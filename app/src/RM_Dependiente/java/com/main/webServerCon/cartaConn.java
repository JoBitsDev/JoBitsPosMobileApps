package com.main.webServerCon;

import com.main.res;

import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 2/8/18.
 */

public class cartaConn  extends simpleConn {


    private String urlNombreRest = "http://"+com.main.res.IP +":8080/RM/rest/com.restmanager.carta/NOMBRE_REST";

    public cartaConn() {
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
