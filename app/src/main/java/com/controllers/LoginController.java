package com.controllers;

import com.services.web_connections.LoginWebConnectionServiceService;
import com.utils.EnvironmentVariables;
import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;

import java.util.concurrent.ExecutionException;

public class LoginController extends BaseController {

    public boolean loginAction(String username, String password)  throws ServerErrorException, NoConnectionException {
        LoginWebConnectionServiceService login = new LoginWebConnectionServiceService(EnvironmentVariables.IP, EnvironmentVariables.PORT, username, password);
        return login.authenticate();
    }
}
