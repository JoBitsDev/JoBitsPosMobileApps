package com.services.models;

import android.util.Base64;

import java.math.BigInteger;
import java.security.MessageDigest;

public class CredentialsModel {

    private String username;
    private String password;

    public CredentialsModel() {
    }

    public CredentialsModel(String username, String password) {
        this.username = username;
        this.password = getSHA256(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBase64BasicAuth(){
        return Base64.encodeToString((username+":"+password).getBytes(), Base64.NO_WRAP);
    }

    private String getSHA256(String pass) {
        try {
            byte arr[] = MessageDigest.getInstance("SHA-256").digest(pass.getBytes());
            return String.format("%064x", new BigInteger(1, arr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "{ \"username\":\"" + username + "\"" +
                ",\"password\":\"" + password + "\" }";
    }
}
