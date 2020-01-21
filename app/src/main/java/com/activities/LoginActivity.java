package com.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.controllers.LoginController;
import com.utils.exception.ExceptionHandler;
import com.utils.exception.NoConnectionException;
import com.utils.exception.ServerErrorException;
import com.utils.loading.LoadingHandler;
import com.utils.loading.LoadingProcess;

/**
 * Capa: Activities
 * Clase que controla el XML del login.
 *
 * @extends BaseActivity ya que es una activity propia de la aplicacion.
 */
public class LoginActivity extends BaseActivity {

    /**
     * Controller del LoginActivity para manejar las peticiones a la capa inferior.
     */
    private LoginController controller;

    //Varialbes de control

    /**
     * Label que indica el estado del logion, correcto o contrasenna incorreco.
     */
    private TextView loginResult;

    /**
     * Boton para loguerse.
     */
    private Button loginButton;

    /**
     * Campo de texto para escribir el usuario.
     */
    private EditText user;

    /**
     * Campo de texto para escribir la contrasenna.
     */
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//xml asociado

        try {
            initVarialbes();//inicializa las variables
            addListeners();//agrega listeners
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        user.setText("");
        pass.setText("");
        loginResult.setText("");
    }

    @Override
    void initVarialbes() {//inicializa las variables
        try {
            controller = new LoginController();//inicializa el controller

            loginResult = (TextView) findViewById(R.id.loginResult);//asigna el label a su variable
            loginButton = (Button) findViewById(R.id.loginButton);//asigna el boton a su variable
            user = (EditText) findViewById(R.id.user);//asigna el campo de texto de usuario a su variable
            pass = (EditText) findViewById(R.id.pass);//asigna el campo de texto de contrasenna a su variable
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    @Override
    void addListeners() {//agrega listeners
        try {
            loginButton.setOnClickListener(new View.OnClickListener() {//listener del boton
                @Override
                public void onClick(View v) {//agrega la accion del click del boton
                    onLoginButtonOnClick(v);
                }
            });
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    /**
     * Accion a ejecutar cuando se da click en el boton del login.
     *
     * @param v View de la aplicacion.
     */
    private void onLoginButtonOnClick(View v) {//accion del click del boton
        try {
            autenticar(v);//llama a autenticar
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    /**
     * Acción a ejecutar cuando se da click en el boton login y se va a autenticar el usuario.
     *
     * @param v View de la aplicacion.
     */
    private void autenticar(View v) {
        try {
            final String username = user.getText().toString();
            final String password = pass.getText().toString();
            final String access = getResources().getString(R.string.access_level);

            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                loginResult.setTextColor(Color.RED);
                loginResult.setText(R.string.errorAlAutenticar);
            } else {

                new LoadingHandler<Boolean>(act, new LoadingProcess<Boolean>() {
                    @Override
                    public Boolean process() throws Exception {
                        return controller.loginAction(username, password);
                    }

                    @Override
                    public void post(Boolean value) {
                        if (value) {
                            loginResult.setTextColor(Color.GREEN);
                            loginResult.setText(R.string.autenticacionCorrecta);

                            //cambio de activity
                            Intent launch = new Intent(act, PantallaPrincipalActivity.class);
                            launch.putExtra(String.valueOf(R.string.user), username);
                            startActivity(launch);

                        } else {//si no es correcto lanza error
                            errorAlAutenticar();
                        }
                    }
                });
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(e, act);
        }
    }

    /**
     * Cambia el estado del label a: error al autenticar.
     */
    private void errorAlAutenticar() {
        loginResult.setTextColor(Color.RED);
        loginResult.setText(R.string.errorAlAutenticar);
    }

}
