package com.main.webServerCon;

import com.main.res;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by Jorge on 19/1/19.
 */

public class cocinaConn extends simpleConn {


    private ArrayList<String> codOrdenes = new ArrayList<String>();
    private final String P = "com.restmanager.cocina/",
            fetchNames =  "NAMES";

    public cocinaConn() {
        super();
    }


    public String[] getCocinasNames(){
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
