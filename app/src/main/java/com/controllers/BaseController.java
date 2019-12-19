package com.controllers;

import com.utils.EnvironmentVariables;

public abstract class BaseController {

    protected final String URLCONN = "http://" + EnvironmentVariables.IP + ":" + EnvironmentVariables.PORT + "/RM";

}
