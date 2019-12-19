package com.controllers;

import com.services.web_connections.LoginWebConnectionServiceService;
import com.utils.EnvironmentVariables;

import java.util.concurrent.ExecutionException;

public class LoginController extends BaseController {

    public boolean loginAction(String username, String password) throws ExecutionException, InterruptedException {
        LoginWebConnectionServiceService login = new LoginWebConnectionServiceService(EnvironmentVariables.IP, EnvironmentVariables.PORT, username, password);
        return login.authenticate();
    }
}
