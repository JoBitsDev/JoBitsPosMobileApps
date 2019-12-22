package com.activities;

import android.widget.*;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.graphics.Color;

import com.controllers.MainController;
import com.services.notifications.ReceiverNotificationService;

/**
 * Capa: Activities
 * Clase que controla el XML del Main.
 *
 * @extends BaseActivity ya que es una activity propia de la aplicacion.
 */
public class MainActivity extends BaseActivity {

    /**
     * Controller del MainActivity para manejar las peticiones a la capa inferior.
     */
    private MainController controller;

    //Varialbes de control

    /**
     * Label con el estado de la coneccion.
     */
    private TextView connectionStatusText;

    /**
     * Boton para activar las notificaciones.
     */
    private Button notificationButton;

    /**
     * Boton para iniciar el flujo de la aplicacion. Boton comenzar.
     */
    private Button initializeSesionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//xml asociado

        initVarialbes();//inicializa las variables
        addListeners();//agrega listeners

        updateConnectionText();//actualiza el label de coneccion
    }

    @Override
    protected void onResume() {//cuando se lanza un error y vira a esta la pantalla, actualiza el label de coneccion
        super.onResume();
        updateConnectionText();//actualiza el label de coneccion
    }

    @Override
    void initVarialbes() {//inicializa las variables
        controller = new MainController();//inicializa el controller

        initializeSesionButton = (Button) findViewById(R.id.initializeSesionButton);//asigna el boton de iniciar a su variable
        notificationButton = (Button) findViewById(R.id.notificationButton);//asigna el boton de notificacion a su variable
        connectionStatusText = ((TextView) (findViewById(R.id.connectionStatusText)));//asigna el label a su variable
    }

    @Override
    void addListeners() {//agrega listeners
        initializeSesionButton.setOnClickListener(new View.OnClickListener() {//agrega la accion del click del boton de iniciar
            @Override
            public void onClick(View v) {//agrega la accion del click del boton
                onInitializeSesionButtOnClick(v);
            }
        });

        notificationButton.setOnClickListener(new View.OnClickListener() {//agrega la accion del click del boton de notificacion
            @Override
            public void onClick(View v) {//agrega la accion del click del boton
                onNotificationButtonOnClick(v);
            }
        });
    }

    /**
     * Accion a ejecutar cuando se da click en el boton de notificacion.
     *
     * @param v View de la aplicacion.
     */
    private void onNotificationButtonOnClick(View v) {
        startNotificationService(v);//inicia el servicio de notificaciones
    }

    /**
     * Accion a ejecutar cuando se da click en el boton de iniciar.
     *
     * @param v View de la aplicacion.
     */
    private void onInitializeSesionButtOnClick(View v) {
        if (updateConnectionText()) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
            this.showMessage(v.getContext().getResources().
                    getText(R.string.noConnectionError).toString());
        }
    }

    /**
     * Actualiza el label de coneccion en dependencia de si hay o no coneccion con el servidor.
     * @return true si hay coneccion con el servidor, false en cualquier otro caso.
     */
    private boolean updateConnectionText() {
        if (controller.checkConnection()) {//hay coneccion con el servidor
            connectionStatusText.setText(R.string.conexion_succesfull);
            connectionStatusText.setTextColor(Color.GREEN);

            return true;
        } else {//NO hay coneccion con el servidor
            connectionStatusText.setText(R.string.no_network);
            connectionStatusText.setTextColor(Color.RED);

            return false;
        }
    }

    /**
     * Inicia el servicio de notificaciones.
     * @param v View de la aplicacion.
     * TODO: ver bien si esto es logica o aqui, y ver la opcion del toggle button
     */
    public void startNotificationService(View v) {
        if (notificationButton.getText().equals("Activar Notificaciones")) {//si esta activado, lo desactiva
            startService(new Intent(MainActivity.this, ReceiverNotificationService.class));
            notificationButton.setText("Desactivar Notificationes");
        } else {//si esta desactivado, lo activa
            stopService(new Intent(MainActivity.this, ReceiverNotificationService.class));
            notificationButton.setText("Activar Notificationes");
        }
    }

}
