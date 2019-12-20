package com.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.controllers.LoginController;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends BaseActivity {

    private LoginController controller;

    private TextView loginResult;
    private Button loginButton;
    private EditText user;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVarialbes();
        addListeners();
    }

    @Override
    void initVarialbes() {
        controller = new LoginController();

        loginResult = (TextView) findViewById(R.id.loginResult);
        loginButton = (Button) findViewById(R.id.loginButton);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
    }

    @Override
    void addListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonOnClick(v);
            }
        });
    }

    private void onLoginButtonOnClick(View v) {
        autenticar(v);
    }

    private void autenticar(View v) {
        String username = user.getText().toString();
        String password = pass.getText().toString();

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            loginResult.setTextColor(Color.RED);
            loginResult.setText(R.string.errorAlAutenticar);
        } else {


            if (controller.checkConnection()) {

                try {
                    if (controller.loginAction(username, password)) {
                        loginResult.setTextColor(Color.GREEN);
                        loginResult.setText(R.string.autenticacionCorrecta);

                        Intent launch = new Intent(this, PantallaPrincipalActivity.class);
                        launch.putExtra(String.valueOf(R.string.user), username);
                        startActivity(launch);

                    } else {
                        loginResult.setTextColor(Color.RED);
                        loginResult.setText(R.string.errorAlAutenticar);
                    }
                } catch (Exception e) {//Nunca entra porque el error lo lanza cuando no hay coneccion por el if de antes
                    super.notificarNoConnection();
                }
            } else {
                super.notificarNoConnection();
            }

        }

    }

}
