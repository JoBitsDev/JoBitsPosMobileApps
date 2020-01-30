package com.services.web_connections;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by Jorge on 19/1/19.
 */

public class CocinaWCS extends SimpleWebConnectionService {

    private final String P = "cocina/",
            fetchNames = "NAMES";

    public CocinaWCS() {
        super();
    }


    public String[] getCocinasNames() {
        try {
            return connect(path + P + fetchNames).split(",");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new String[0];
    }


}
