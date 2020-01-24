package com.services.web_connections;

public enum HTTPMethod {
    GET("GET"), POST("POST"), DELETE("DELETE"), PUT("PUT");
    private final String method;

    HTTPMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
