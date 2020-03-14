package com.services.models;

import com.services.web_connections.HTTPMethod;

public class RequestModel {
    private String urlToExcecute;
    private String body;
    private String token;
    private HTTPMethod method;
    private RequestType type = RequestType.NORMAL;
    private String uid;

    public RequestModel(String urlToExcecute, String body, String token, HTTPMethod method) {
        this.urlToExcecute = urlToExcecute;
        this.body = body;
        this.token = token;
        this.method = method;
    }

    public RequestModel(String urlToExcecute, String body, String token, HTTPMethod method, RequestType type) {
        this.urlToExcecute = urlToExcecute;
        this.body = body;
        this.token = token;
        this.method = method;
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getUrlToExcecute() {
        return urlToExcecute;
    }

    public void setUrlToExcecute(String urlToExcecute) {
        this.urlToExcecute = urlToExcecute;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HTTPMethod getMethod() {
        return method;
    }

    public void setMethod(HTTPMethod method) {
        this.method = method;
    }
}
