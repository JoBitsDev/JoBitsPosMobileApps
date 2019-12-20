package com.services.web_connections;

import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 24/9/17.
 */

public class LoginWebConnectionServiceService extends SimpleWebConnectionService {

    private String user, pass;

    public LoginWebConnectionServiceService(String ip, String port, String user, String pass) {
        super(ip, port);
        this.user = user;
        this.pass = pass;
    }

    public boolean authenticate() throws ExecutionException, InterruptedException {
        return connect(path + "com.restmanager.personal/l_" + user + "_" + pass).equals("1");
    }


}
